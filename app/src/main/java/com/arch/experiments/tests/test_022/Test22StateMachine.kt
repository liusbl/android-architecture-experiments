package com.arch.experiments.tests.test_022

import com.arch.experiments.tests.test_022.lib.StateMachineImpl

// TODO how to make it so there is no reaction to initial state?? Or is this correct
class Test22StateMachine : StateMachineImpl<Test22StateMachine.State>() {
    override fun start() {
        observe({ text1 }, { push { copy(text2 = "$text2*") } })
        observe({ text2 }, { push { copy(text1 = "$text1+") } })
    }

    data class State(val text1: String, val text2: String)
}