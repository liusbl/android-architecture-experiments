package com.arch.experiments.tests.test_025

import com.arch.experiments.tests.test_025.lib.Action
import com.arch.experiments.tests.test_025.lib.StateMachineImpl

// TODO how to make it so there is no reaction to initial state?? Or is this correct
class Test25StateMachine : StateMachineImpl<Test25StateMachine.State>() {
    override fun start() {
        observe({ clickAction }, { push { copy(clickCount = clickCount + 1) } })
    }

    data class State(val clickCount: Int, val clickAction: Action)
}