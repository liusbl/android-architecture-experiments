package com.arch.experiments.tests.test_019.lib

class StateMachineHandler<State> {
    private val stateMachines = mutableListOf<StateMachine<State>>()

    fun addStateMachine(stateMachine: StateMachine<State>) {
        stateMachines.add(stateMachine)
    }

    fun push(
        stateMachine: StateMachine<State>,
        updateState: State.() -> State
    ) {
        stateMachines.filterNot { it == stateMachine }
            .firstOrNull()
            ?.push(updateState)
    }

    fun observe(
        stateMachine: StateMachine<State>,
        trigger: State.() -> Any,
        reaction: State.() -> Unit
    ) {
        stateMachines.filterNot { it == stateMachine }
            .firstOrNull()
            ?.observe(trigger, reaction)
    }

//    fun addStateMachine() {
//        stateMachines.forEach { machine ->
//            machine.observe()
//            // When you observe from one machine, then push to the other ones.
//        }
//    }
}