package com.arch.experiments.tests.test_037

import androidx.annotation.ColorRes
import com.arch.experiments.R
import com.arch.experiments.tests.test_037.lib.BaseViewModel
import com.arch.experiments.tests.test_037.lib.ToggleAction
import com.arch.experiments.tests.test_037.lib.ToggleEffect
import com.arch.experiments.tests.test_037.misc.MyPreferences
import io.reactivex.Observable
import io.reactivex.Single

class Test37ViewModel(
    private val preferences: MyPreferences
) : BaseViewModel<Test37ViewModel.State>(
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
    }

    private fun actionsToNothing() {
        onActionToNothing(State::changePreferenceValueInstantlyAction) { preferences.incrementUpdateCount() }

        onActionToNothingCompletable(
            State::changePreferenceValueAsyncAction,
            preferences.incrementUpdateCountAsync()
        )
    }

    private fun actionsToEffects() {
        onActionToEffect(State::emitEffectInstantlyAction, State::toastEffect)

        onActionToEffectList(
            State::emitTwoEffectsInstantlyAction,
            State::toastEffect,
            State::popupEffect
        )

        onActionToEffectSingle(State::emitEffectAsyncAction, Single.just(State::toastEffect))

        onActionToEffectObservable(
            State::emitTwoEffectsAsyncAction,
            Observable.just(State::toastEffect, State::popupEffect)
        )
    }

    private fun actionsToStates() {
        onActionToState(State::changeStateInstantlyAction) {
            copy(
                changeStateInstantlyButtonColor =
                if (changeStateInstantlyButtonColor == R.color.red) R.color.blue else R.color.red
            )
        }

        val a: State.() -> State = {
            copy(
                changeStateAsyncButtonColor =
                if (changeStateAsyncButtonColor == R.color.green) R.color.yellow else R.color.green
            )
        }
        onActionToStateSingle(State::changeStateAsyncAction, Single.just(a))//TODO: Can't inline??


        val b: State.() -> State = {
            copy(
                changeStateAsyncButtonColor =
                if (changeTwoStatesAsyncButtonColor == R.color.magenta) R.color.cyan else R.color.magenta
            )
        }
        onActionToStateObservable(
            State::changeTwoStatesAsyncAction,
            Observable.just(b)
        ) // TODO: Can't inline?
    }

    private fun statesToNothing() {
        onStateToNothing(State::changePreferenceValueInstantlyText) {
            preferences.incrementUpdateCount()
        }

        onStateToNothingCompletable(
            State::changePreferenceValueAsyncText,
            preferences.incrementUpdateCountAsync()
        )
    }

    private fun statesToEffects() {
        onStateToEffect(State::emitEffectInstantlyText, State::toastEffect)

        onStateToEffectList(
            State::emitTwoEffectsInstantlyText,
            State::toastEffect,
            State::popupEffect
        )

        val toastEffect: State.() -> ToggleEffect =
            State::toastEffect // TODO cannot inline and must specify type
        val popupEffect = State::popupEffect // TODO cannot inline and must specify type

        onStateToEffectSingle(State::emitEffectAsyncText, Single.just(toastEffect))

        onStateToEffectObservable(
            State::emitTwoEffectsAsyncText,
            Observable.just(toastEffect, popupEffect)
        )
    }

    // TODO make this only notify from the view, as well as other methods.
    private fun statesToStates() {
        onStateToState(
            State::changeStateInstantlyText1,
            {
                if (changeStateInstantlyText1 == "" && changeStateInstantlyText2.endsWith("+")) {
                    this
                } else {
                    copy(changeStateInstantlyText2 = "$changeStateInstantlyText2+")
                }
            }
        )

        onStateToState(
            State::changeStateInstantlyText2,
            {
                if (changeStateInstantlyText2 == "" && changeStateInstantlyText1.endsWith("*")) {
                    this
                } else {
                    copy(changeStateInstantlyText1 = "$changeStateInstantlyText1*")
                }
            }
        )

//        val state1: State.() -> State = { copy(changeStateAsyncText2 = "$changeStateAsyncText2+") }
//        onStateToStateSingle(
//            State::changeStateAsyncText1,
//            Single.just(state1)
//        )
//
//        val state2: State.() -> State = { copy(changeStateAsyncText1 = "$changeStateAsyncText1*") }
//        onStateToStateSingle(
//            State::changeStateAsyncText2,
//            Single.just(state2)
//        )
//
//        onStateToStateObservable(
//            State::changeTwoStatesAsyncText1,
//            Observable.just(state1)
//        )
//
//        onStateToStateObservable(
//            State::changeTwoStatesAsyncText1,
//            Observable.just(state2)
//        )
    }

    data class State(
        // PTR: Actions To Nothing
        val changePreferenceValueInstantlyAction: ToggleAction = ToggleAction.Inactive,
        val changePreferenceValueAsyncAction: ToggleAction = ToggleAction.Inactive,
        // PTR: Actions To Effects
        val emitEffectInstantlyAction: ToggleAction = ToggleAction.Inactive,
        val emitTwoEffectsInstantlyAction: ToggleAction = ToggleAction.Inactive,
        val emitEffectAsyncAction: ToggleAction = ToggleAction.Inactive,
        val emitTwoEffectsAsyncAction: ToggleAction = ToggleAction.Inactive,
        // PTR: Actions To States
        val changeStateInstantlyAction: ToggleAction = ToggleAction.Inactive,
        val changeStateAsyncAction: ToggleAction = ToggleAction.Inactive,
        val changeTwoStatesAsyncAction: ToggleAction = ToggleAction.Inactive,
        // PTR: Just Effects
        val toastEffect: ToggleEffect = ToggleEffect.Inactive,
        val popupEffect: ToggleEffect = ToggleEffect.Inactive,
        // PTR: Just States
        @ColorRes val changeStateInstantlyButtonColor: Int = R.color.red,
        @ColorRes val changeStateAsyncButtonColor: Int = R.color.green,
        @ColorRes val changeTwoStatesAsyncButtonColor: Int = R.color.magenta,
        val changePreferenceValueInstantlyText: String = "",
        val changePreferenceValueAsyncText: String = "",
        val emitEffectInstantlyText: String = "",
        val emitTwoEffectsInstantlyText: String = "",
        val emitEffectAsyncText: String = "",
        val emitTwoEffectsAsyncText: String = "",
        val changeStateInstantlyText1: String = "",
        val changeStateInstantlyText2: String = "",
        val changeStateAsyncText1: String = "",
        val changeStateAsyncText2: String = "",
        val changeTwoStatesAsyncText1: String = "",
        val changeTwoStatesAsyncText2: String = ""
    )
}

data class ListItem(val id: Int)


// PTR: Actions but with data passed to effect: TODO

// PTR: Actions but with data passed to action: TODO

// PTR: Actions but with data passed to action and effect: TODO

// PTR: Observable Actions (actions updates as observable stream)TODO
//    fun onObservableActionToEmpty(getToggle: State.() -> ToggleAction, action: Observable<Unit>??? or State?? -> Unit) {
//        // TODO
//    }
//
//    fun onObservableActionToEmptyCompletableStream(getToggle: State.() -> ToggleAction, action: Completable) {
//        // TODO
//    }
//
//    fun onObservableActionToEffect(getToggleh: State.() -> ToggleAction, getEffect: State.() -> ToggleEffect) {
//        // TODO
//    }
//
//    fun onObservableActionToEffectList(getToggle: State.() -> ToggleAction, vararg getEffect: State.() -> ToggleEffect) {
//        // TODO
//    }
//
//    fun onObservableActionToEffectSingleStream(getToggle: State.() -> ToggleAction, getEffect: Single<State.() -> ToggleEffect>) {
//        // TODO
//    }
//
//    fun onObservableActionToEffectObservableStream(getToggle: State.() -> ToggleAction, getEffect: Observable<State.() -> ToggleEffect>) {
//        // TODO
//    }



