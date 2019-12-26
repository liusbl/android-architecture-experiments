package com.arch.experiments.tests.test_077.lib

interface Machine<State> {
    val state: State

    fun observe(onChanged: (State) -> Unit)

    fun push(newState: State = state)
}