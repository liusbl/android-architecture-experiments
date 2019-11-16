package com.arch.experiments.tests.test_019

import com.arch.experiments.tests.test_019.Test19StateMachine.State
import com.arch.experiments.tests.test_019.lib.Action
import com.arch.experiments.tests.test_019.lib.StateMachineHandler
import com.arch.experiments.tests.test_019.lib.StateMachineImpl

class Test19StateMachine(
    stateMachineHandler: StateMachineHandler<State>
) : StateMachineImpl<State>(stateMachineHandler) {
    override fun toggle(updateState: State.(Action) -> State) {
        // Empty
    }

    override fun observeToggle(
        trigger: State.() -> Action,
        reaction: State.() -> Unit
    ) {
        // Empty
    }

    data class State(val name: String = "")
}