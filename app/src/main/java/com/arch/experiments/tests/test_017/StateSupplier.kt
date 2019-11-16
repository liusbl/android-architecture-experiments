package com.arch.experiments.tests.test_017

interface StateSupplier<State> {
    fun push(updateState: State.() -> State)
}