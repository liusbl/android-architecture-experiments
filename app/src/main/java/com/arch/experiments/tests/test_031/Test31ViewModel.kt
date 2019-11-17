package com.arch.experiments.tests.test_031

import com.arch.experiments.tests.test_031.Test31ViewModel.State
import com.arch.experiments.tests.test_031.lib.Action
import com.arch.experiments.tests.test_031.lib.BaseViewModel
import com.arch.experiments.tests.test_031.lib.Effect
import com.arch.experiments.tests.test_031.lib.ToggleEffect
import com.arch.experiments.tests.test_031.misc.Database
import com.arch.experiments.tests.test_031.misc.Greeter

class Test31ViewModel(
    private val db: Database,
    private val greeter: Greeter
) : BaseViewModel<State>(
    // TODO: This initialization is bloat'y
    State(
        Effect { null },
        ToggleEffect.Inactive,
        Action.Inactive,
        Action.Inactive,
        false,
        "",
        ""
    )
) {

    // TODO onState to state changes and things

    data class State(
        val failureEffect: Effect<Exception?>, // Effect
        val successEffect: ToggleEffect, // Effect
        val viewCreatedAction: Action, // Action
        val greetClickedAction: Action, // Action
        val isLoading: Boolean, // State
        val name: String, // State
        val greeting: String // State
    )
}