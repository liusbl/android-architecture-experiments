package com.arch.experiments.tests.test_040.test_with_sigis

open class StateMachineImpl<State> : StateMachine<State> {
    var state: State = null!!

    override fun updateState(updateState: (State) -> State) {
        state = updateState(state)
    }

    override fun observeState(trigger: (State) -> Any, reaction: (State) -> Unit) {
        // TODO
    }
}