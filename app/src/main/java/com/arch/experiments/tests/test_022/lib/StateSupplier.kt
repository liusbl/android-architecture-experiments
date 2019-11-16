package com.arch.experiments.tests.test_022.lib

interface StateSupplier<State> {
    fun push(updateState: State.() -> State)

    fun toggle(updateState: State.(Action) -> State)
}