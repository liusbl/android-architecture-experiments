package com.arch.experiments.tests.test_034

import com.arch.experiments.tests.test_034.Test34ViewModel.State
import com.arch.experiments.tests.test_034.lib.*
import com.arch.experiments.tests.test_034.misc.Database
import com.arch.experiments.tests.test_034.misc.Greeter

class Test34ViewModel(
    private val db: Database,
    private val greeter: Greeter
) : BaseViewModel<State>(
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