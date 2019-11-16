package com.arch.experiments.tests.test_017

interface StateObserver<State> {
    fun react(
        reactionTrigger: State.() -> Any,
        stateSupplier: State.((setState: State) -> Unit) -> Unit
    )
}