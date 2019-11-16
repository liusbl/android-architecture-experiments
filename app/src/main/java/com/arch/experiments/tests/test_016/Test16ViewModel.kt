package com.arch.experiments.tests.test_016

import com.arch.experiments.tests.test_016.Test16ViewModel.State
import com.arch.experiments.tests.test_016.misc.Greeter

class Test16ViewModel(
    greeter: Greeter
) : BaseViewModel<State>(State()) {
    init {
        onAction(
            { greetClickedAction },
            pushEffect { effect ->
                copy(
                    isGreetingLoading = true,
                    animateButtonEffect = effect,
                    showGreetStartedEffect = effect
                )
            }.mergeWith(
                // TODO: Not latest state, greetName will be empty
                greeter.createGreeting(state.greetName)
                    .toObservable()
                    .flatMap { greeting ->
                        pushEffect { effect ->
                            copy(
                                isGreetingLoading = false,
                                showGreetFinishedEffect = effect,
                                greeting = greeting
                            )
                        }
                    }
            )
        )
    }

    data class State(
        val greetClickedAction: ToggleAction = ToggleAction.Inactive,
        val greetName: String = "",
        val greeting: String = "",
        val isGreetingLoading: Boolean = false,
        val animateButtonEffect: ToggleEffect = ToggleEffect.Inactive, // PTR: These two shown at the same time
        val showGreetStartedEffect: ToggleEffect = ToggleEffect.Inactive, // PTR: These two shown at the same time
        val showGreetFinishedEffect: ToggleEffect = ToggleEffect.Inactive
    )
}