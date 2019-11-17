package com.arch.experiments.tests.test_030

import com.arch.experiments.tests.test_030.Test30ViewModel.State
import com.arch.experiments.tests.test_030.lib.Action
import com.arch.experiments.tests.test_030.lib.BaseViewModel
import com.arch.experiments.tests.test_030.lib.Effect
import com.arch.experiments.tests.test_030.misc.Database
import com.arch.experiments.tests.test_030.misc.Greeter

class Test30ViewModel(
    private val db: Database,
    private val greeter: Greeter
) : BaseViewModel<State>(
    // TODO: This initialization is bloat'y
    State(
        Effect.Inactive,
        null,
        Effect.Inactive,
        Action.Inactive,
        Action.Inactive,
        false,
        "",
        ""
    )
) {

    data class State(
        // PTR: In this scenario, when isFailureShowing and failureException are different,
        //  Toast has to set error and show something at different methods
        //  But maybe this isn't that bad, maybe it's how it should be done.
        val isFailureShowing: Effect, // Effect
        val failureException: Exception?, // State
        val isSuccessShowing: Effect, // Effect
        val viewCreated: Action, // Action
        val greetClicked: Action, // Action
        val isLoading: Boolean, // State
        val name: String, // State
        val greeting: String // State
    )

}