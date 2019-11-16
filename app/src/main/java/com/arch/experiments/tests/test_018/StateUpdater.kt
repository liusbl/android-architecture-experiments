package com.arch.experiments.tests.test_018

interface StateUpdater<State> {
    fun push(updateState: State.() -> State)

    fun react(reactionTrigger: State.() -> Any, react: State.() -> Unit)
}