package com.arch.experiments.tests.test_023

import com.arch.experiments.tests.test_023.lib.StateMachineImpl

// TODO how to make it so there is no reaction to initial state?? Or is this correct
class Test23StateMachine : StateMachineImpl<Test23StateMachine.State>() {
    override fun start() {
        observe({ password }, { push { copy(isPasswordValid = password.length > 6) } })
    }

    data class State(val password: String, val isPasswordValid: Boolean)
}