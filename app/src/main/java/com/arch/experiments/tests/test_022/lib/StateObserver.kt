package com.arch.experiments.tests.test_022.lib

interface StateObserver<State, R> {
    fun observe(
        trigger: State.() -> Any,
        reaction: State.() -> R
    )

    fun observeToggle(
        trigger: State.() -> Action,
        reaction: State.() -> R
    )
}
