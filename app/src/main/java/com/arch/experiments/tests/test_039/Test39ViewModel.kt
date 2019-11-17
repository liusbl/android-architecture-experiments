package com.arch.experiments.tests.test_039

import androidx.annotation.ColorRes
import com.arch.experiments.R
import com.arch.experiments.tests.test_039.Test39ViewModel.State
import com.arch.experiments.tests.test_039.lib.BaseViewModel
import com.arch.experiments.tests.test_039.lib.ToggleAction
import com.arch.experiments.tests.test_039.lib.ToggleEffect
import com.arch.experiments.tests.test_039.misc.MyPreferences
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class Test39ViewModel(
    private val preferences: MyPreferences
) : BaseViewModel<State>(
// TODO: This initialization is bloat'y?
//  Maybe all those Toggle actions could be set magically, since they should be inactive by default?
    State()
) {
    init {
        actionsToNothing()
        actionsToEffects()
        actionsToStates()
        statesToNothing()
        statesToEffects()
        statesToStates()

        example1_greeting()
    }

    private fun actionsToNothing() {
        onAction(State::changePreferenceValueInstantlyAction) {
            preferences.incrementUpdateCount()
            state // TODO not fun that you have to add this boilerplate
        }

        onAction(
            State::changePreferenceValueAsyncAction,
            preferences.incrementUpdateCountAsync()
                .toObservable()
        )
    }

    private fun actionsToEffects() {
        onAction(State::emitEffectInstantlyAction, state.withEffect { copy(toastEffect = it) })

        onAction(
            State::emitEffectAsyncAction,
            state.withEffect {
                copy(
                    toastEffect = it,
                    popupEffect = it
                )
            }
        )
    }

    private fun actionsToStates() {
        onAction(State::changeStateInstantlyAction) {
            state.copy(
                changeStateInstantlyButtonColor =
                if (state.changeStateInstantlyButtonColor == R.color.red) R.color.blue
                else R.color.red
            )
        }

        onAction(
            State::changeStateAsyncAction,
            Observable.just({
                state.copy(
                    changeStateAsyncButtonColor =
                    if (state.changeStateAsyncButtonColor == R.color.green) R.color.yellow
                    else R.color.green
                )
            })
        )
    }

    private fun statesToNothing() {
        onState(State::changePreferenceValueInstantlyText) {
            preferences.incrementUpdateCount()
            state
        }

        onState(
            State::changePreferenceValueAsyncText,
            preferences.incrementUpdateCountAsync()
                .toObservable()
        )
    }

    private fun statesToEffects() {
        onState(State::emitEffectInstantlyText, state.withEffect { copy(toastEffect = it) })

        onState(
            State::emitEffectAsyncText,
            state.withEffect { copy(toastEffect = it) }
        )
    }

    // TODO make this only notify from the view, as well as other methods.
    private fun statesToStates() {
        onState(
            State::changeStateInstantlyText1,
            {
                if (state.changeStateInstantlyText1 == "" && state.changeStateInstantlyText2.endsWith(
                        "+"
                    )
                ) {
                    state
                } else {
                    state.copy(changeStateInstantlyText2 = "${state.changeStateInstantlyText2}+")
                }
            }
        )

        onState(
            State::changeStateInstantlyText2,
            {
                if (state.changeStateInstantlyText2 == "" && state.changeStateInstantlyText1.endsWith(
                        "+"
                    )
                ) {
                    state
                } else {
                    state.copy(changeStateInstantlyText1 = "${state.changeStateInstantlyText1}*")
                }
            }
        )
    }

    private fun example1_greeting() {
        fun createGreeting(name: String): Observable<String> {
            return Observable.timer(2, TimeUnit.SECONDS)
                .map { "Hello, $name" }
        }

        onAction(
            State::greetClickedAction,
            createGreeting(state.greetName)
                .map { state.copy(greeting = it) }
                .startWith {
                    state.withEffect { effect ->
                        copy(
                            isGreetingLoading = true,
                            animateButtonEffect = effect, // TODO Check if this works
                            showGreetStartedEffect = effect // TODO Check if this works
                        )
                    }
                }
                .flatMap { state.withEffect { copy(showGreetFinishedEffect = it) } }
        )
    }


    data class State(
        // PTR: Actions To Nothing
        val changePreferenceValueInstantlyAction: ToggleAction = ToggleAction.Inactive,
        val changePreferenceValueAsyncAction: ToggleAction = ToggleAction.Inactive,
        // PTR: Actions To Effects
        val emitEffectInstantlyAction: ToggleAction = ToggleAction.Inactive,
        val emitEffectAsyncAction: ToggleAction = ToggleAction.Inactive,
        // PTR: Actions To States
        val changeStateInstantlyAction: ToggleAction = ToggleAction.Inactive,
        val changeStateAsyncAction: ToggleAction = ToggleAction.Inactive,
        // PTR: Just Effects
        val toastEffect: ToggleEffect = ToggleEffect.Inactive,
        val popupEffect: ToggleEffect = ToggleEffect.Inactive,
        // PTR: Just States
        @ColorRes val changeStateInstantlyButtonColor: Int = R.color.red,
        @ColorRes val changeStateAsyncButtonColor: Int = R.color.green,
        val changePreferenceValueInstantlyText: String = "",
        val changePreferenceValueAsyncText: String = "",
        val emitEffectInstantlyText: String = "",
        val emitEffectAsyncText: String = "",
        val changeStateInstantlyText1: String = "",
        val changeStateInstantlyText2: String = "",
        val changeStateAsyncText1: String = "",
        val changeStateAsyncText2: String = "",
        // PTR: COMBINATIONS
        // PTR: Example 1: Greeting
        val greetClickedAction: ToggleAction = ToggleAction.Inactive,
        val greetName: String = "",
        val greeting: String = "",
        val isGreetingLoading: Boolean = false,
        val animateButtonEffect: ToggleEffect = ToggleEffect.Inactive, // PTR: These two shown at the same time
        val showGreetStartedEffect: ToggleEffect = ToggleEffect.Inactive, // PTR: These two shown at the same time
        val showGreetFinishedEffect: ToggleEffect = ToggleEffect.Inactive
    )
}

data class ListItem(val id: Int)


// PTR: Actions but with data passed to effect: TODO

// PTR: Actions but with data passed to action: TODO

// PTR: Actions but with data passed to action and effect: TODO

// PTR: Observable Actions (actions updates as observable stream)TODO
