package com.arch.experiments.tests.test_025.lib

open class StateMachineImpl<State> : StateMachine<State> {
    private lateinit var stateMachine: GlobalStateMachine<State>

    val state: State = TODO()

    final override fun start(stateMachine: GlobalStateMachine<State>) {
        this.stateMachine = stateMachine
        start()
    }

    protected open fun start() {
        // Empty
    }

    override fun push(updateState: State.() -> State) {
        stateMachine.push(this, updateState)
    }

    override fun toggle(updateState: State.(Action) -> State) {
        stateMachine.toggle(this, updateState)
    }

    override fun observe(trigger: State.() -> Any, reaction: State.() -> Unit) {
        stateMachine.observe(this, trigger, reaction)
    }

    override fun observeToggle(trigger: State.() -> Action, reaction: State.() -> Unit) {
        stateMachine.observeToggle(this, trigger, reaction)
    }
}