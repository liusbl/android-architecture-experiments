package com.arch.experiments.tests.test_025.lib

interface StateMachine<State> : StateSupplier<State>, StateObserver<State, Unit> {
    fun start(stateMachine: GlobalStateMachine<State>)
}

interface StateSupplier<State> {
    fun push(updateState: State.() -> State)

    fun toggle(updateState: State.(Action) -> State)
}

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
