package com.arch.experiments.tests.test_036

import androidx.annotation.ColorRes
import com.arch.experiments.R
import com.arch.experiments.tests.test_036.lib.BaseViewModel
import com.arch.experiments.tests.test_036.lib.ToggleAction
import com.arch.experiments.tests.test_036.lib.ToggleEffect
import com.arch.experiments.tests.test_036.misc.MyPreferences
import io.reactivex.Observable
import io.reactivex.Single

class Test36ViewModel(
    private val preferences: MyPreferences
) : BaseViewModel<Test36ViewModel.State>(
    // TODO: This initialization is bloat'y?
    //  Maybe all those Toggle actions could be set magically, since they should be inactive by default?
    State()
) {

    override fun handleEverything() {
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
        onStateToNothing(State::changePreferenceValueInstantlyText) { preferences.incrementUpdateCount() }

        onStateToNothingCompletable(
            State::changePreferenceValueAsyncText,
            preferences.incrementUpdateCountAsync()
        )

        val toastEffect: State.() -> ToggleEffect = State::toastEffect // TODO cannot inline and must specify type
        val popupEffect: State.() -> ToggleEffect = State::toastEffect // TODO cannot inline and must specify type

        onStateToEffectSingle(State::emitEffectAsyncText, Single.just(toastEffect))

        onStateToEffectObservable(
            State::emitTwoEffectsAsyncText,
            Observable.just(State::toastEffect, popupEffect)
        )
    }

    private fun statesToEffects() {
        onStateToEffect(State::emitEffectInstantlyText, State::toastEffect)

        onStateToEffectList(
            State::emitTwoEffectsInstantlyText,
            State::toastEffect,
            State::popupEffect
        )
    }

    private fun statesToStates() {

    }

    // TODO: the following things should be in base class
    fun <T> onState(getToggle: State.() -> T, modifyState: State.(T) -> State) {

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
        val changeStateInstantlyText: String = "",
        val changeStateAsyncText: String = "",
        val changeTwoStatesAsyncText: String = ""
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
//    fun onObservableActionToEffect(getToggle: State.() -> ToggleAction, getEffect: State.() -> ToggleEffect) {
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


