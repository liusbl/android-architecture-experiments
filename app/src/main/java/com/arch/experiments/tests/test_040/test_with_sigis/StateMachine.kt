package com.arch.experiments.tests.test_040.test_with_sigis

interface StateMachine<State> {
    fun updateState(state: (State) -> State)

    fun observeState(trigger: (State) -> Any, reaction: (State) -> Unit)
}