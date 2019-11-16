package com.arch.experiments.tests.test_019.lib

open class StateMachineImpl<State>(
    private val muchStateMachineHandler: StateMachineHandler<State>
) : StateMachine<State> {
    override fun start(stateMachine: GlobalStateMachine<State>) {
        // Empty
    }

    override fun toggle(updateState: State.(Action) -> State) {
        // Empty
    }

    override fun observeToggle(trigger: State.() -> Action, reaction: State.() -> Unit) {
        // Empty
    }

    override fun push(updateState: State.() -> State) {
        muchStateMachineHandler.push(this, updateState)
    }

    override fun observe(trigger: State.() -> Any, reaction: State.() -> Unit) {
        muchStateMachineHandler.observe(this, trigger, reaction)
    }
}