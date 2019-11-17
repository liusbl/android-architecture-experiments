package com.arch.experiments.tests.test_032

import com.arch.experiments.tests.test_032.Test32ViewModel.State
import com.arch.experiments.tests.test_032.lib.*
import com.arch.experiments.tests.test_032.misc.Database
import com.arch.experiments.tests.test_032.misc.Greeter

class Test32ViewModel(
    private val db: Database,
    private val greeter: Greeter
) : BaseViewModel<State>(
    // TODO: This initialization is bloat'y
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

    // TODO onState to state changes and things

    data class State(
        val failureEffect: Effect<Exception?>, // Effect
        val successEffect: ToggleEffect, // Effect
        val viewCreatedAction: ToggleAction, // Action
        val greetClickedAction: ToggleAction, // Action
        val listItemClicked: Action<ListItem?>, // Action
        val isLoading: Boolean, // State
        val name: String, // State
        val greeting: String // State
    )
}