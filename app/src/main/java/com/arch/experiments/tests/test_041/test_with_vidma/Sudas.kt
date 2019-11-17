package com.arch.experiments.tests.test_041.test_with_vidma

interface Sudas<State> {
    fun pushState(updateState: State.() -> State)

    fun observe(trigger: State.() -> Any, react: State.() -> Unit)
}