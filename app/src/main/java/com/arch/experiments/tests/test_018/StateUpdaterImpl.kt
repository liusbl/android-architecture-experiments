package com.arch.experiments.tests.test_018

open class StateUpdaterImpl<State> : StateUpdater<State> {
    override fun push(updateState: State.() -> State) {
        // Empty
    }

    override fun react(reactionTrigger: State.() -> Any, react: State.() -> Unit) {
        // Empty
    }
}