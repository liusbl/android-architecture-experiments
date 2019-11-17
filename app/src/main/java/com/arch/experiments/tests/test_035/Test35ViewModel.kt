package com.arch.experiments.tests.test_035

import com.arch.experiments.tests.test_035.Test35ViewModel.State
import com.arch.experiments.tests.test_035.lib.*
import com.arch.experiments.tests.test_036.misc.Database
import com.arch.experiments.tests.test_036.misc.Greeter
import io.reactivex.Observable

class Test35ViewModel(
    private val db: Database,
    private val greeter: Greeter
): BaseViewModel<State>(
    // TODO: This initialization is bloat'y? Maybe All those Toggle actions could be set magically?
    State(
        EmptyEffect(),
        ToggleEffect.Inactive,
        ToggleAction.Inactive,
        ToggleAction.Inactive,
        EmptyAction(),
        false,
        "",
        ""
    )
) {

    fun a() {
        onAction(State::greetClickedAction) {
            greeter.createGreeting(name)
                .map { greeting -> copy(greeting = greeting) }
        }

        onAction(State::listItemClickedAction) { item: ListItem ->
            // Another things
            Observable.empty()
        }

//        onState(State::greeting) {
//
//        }
    }

    // TODO CREATE BBETTER EXAMPLES

    fun <T> onState(getToggle: State.() -> T, stream: State.() -> Observable<State>) {

    }

    fun onAction(getToggle: State.() -> ToggleAction, stream: State.() -> Observable<State>) {
        // TODO
    }

    fun <T> onAction(getToggle: State.() -> Action<T>, stream: State.(T) -> Observable<State>) {
        // TODO
    }

    // TODO onState to state changes and things
    data class State(
        val failureEffect: Effect<Exception?>, // TODO nullability is annoying
        val successEffect: ToggleEffect,
        val viewCreatedAction: ToggleAction,
        val greetClickedAction: ToggleAction,
        val listItemClickedAction: Action<ListItem>, // TODO nullability is annoying
        val isLoading: Boolean,
        val name: String,
        val greeting: String
    )
}