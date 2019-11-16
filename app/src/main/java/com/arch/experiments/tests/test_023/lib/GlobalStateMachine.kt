package com.arch.experiments.tests.test_023.lib

// PTR: Good guide it to try to minimize actions and maximize states

// PTR: it becomes very easy to reason about and by consequence test, because you only have to provide a resulting state list and some conditions


// TODO make it so that you are protected from infinite loops when pushing in observe method
//      While in debug it could do some checks or someting
// TODO could calculate some "state change speed/frequency"  or something

// TODO time travel

// TODO Should make versions optimized for two state machines and separately  for list of state machines, maybe
class GlobalStateMachine<State>(
    private val machine1: StateMachine<State>,
    private val machine2: StateMachine<State>,
    initialState: State
) {
    private var state: State = initialState
    private val observation1List = mutableListOf<Observation<State>>()
    private val observation2List = mutableListOf<Observation<State>>()
    private var onStateUpdate: State.() -> Unit = {}

    // PTR: Useful for testing maybe
    fun onStateUpdate(action: State.() -> Unit) {
        onStateUpdate = action
        action(state)
    }

    fun start() {
        machine1.start(this)
        machine2.start(this)
    }

    fun toggle(stateMachine: StateMachine<State>, updateState: State.(Action) -> State) {
        // TODO: NO STATE CHANGE SHOULD COME IN BETWEEN ACTION TOGGLES,
        //  THEREFORE THIS IS INCORRECT
        //  SHOULD BE COVERED WITH UNIT TESTS

        state = state.updateState(Action.ACTIVE) // TODO check if Not synchronized?
        onStateUpdate(state)
        react(stateMachine)

        state = state.updateState(Action.INACTIVE) // TODO check if Not synchronized?
        onStateUpdate(state)
        react(stateMachine)
    }

    fun push(stateMachine: StateMachine<State>, updateState: State.() -> State) {
        state = state.updateState() // TODO check if Not synchronized?
        onStateUpdate(state)
        react(stateMachine)
    }

    private fun react(
        stateMachine: StateMachine<State>
    ) {
        when {
            stateMachine === machine1 -> {
                observation1List.forEach { observation ->
                    observation.previousTrigger = observation.trigger(state)
                }
                observation2List.forEach { observation ->
                    val trigger = observation.trigger(state)
                    if (trigger != observation.previousTrigger) {
                        observation.previousTrigger = trigger
                        observation.reaction(state)
                    }
                }
            }
            stateMachine === machine2 -> {
                observation2List.forEach { observation ->
                    observation.previousTrigger = observation.trigger(state)
                }
                observation1List.forEach { observation ->
                    val trigger = observation.trigger(state)
                    if (trigger != observation.previousTrigger) {
                        observation.previousTrigger = trigger
                        observation.reaction(state)
                    }
                }
            }
            else -> {
                throw Exception("Using invalid state machine")
            }
        }
    }

    // TODO how to make it so there is no reaction to initial state??
    fun observe(
        stateMachine: StateMachine<State>,
        trigger: State.() -> Any,
        reaction: State.() -> Unit
    ) {
        reaction(state) // PTR: if we want to  not notify observers on initial state then comment out this line
        when {
            stateMachine === machine1 -> observation1List.add(
                Observation(
                    state.trigger(),
                    trigger,
                    reaction
                )
            )
            stateMachine === machine2 -> observation2List.add(
                Observation(
                    state.trigger(),
                    trigger,
                    reaction
                )
            )
            else -> throw Exception("Using invalid state machine")
        }
    }

    // TODO how to make it so there is no reaction to initial state??
    fun observeToggle(
        stateMachine: StateMachine<State>,
        trigger: State.() -> Action,
        reaction: State.() -> Unit
    ) {
//        reaction(state) // PTR: if we want to  not notify observers on initial state then comment out this line
//        when {
//            stateMachine === machine1 -> observation1List.add(
//                Observation(
//                    state.trigger(),
//                    trigger,
//                    reaction
//                )
//            )
//            stateMachine === machine2 -> observation2List.add(
//                Observation(
//                    state.trigger(),
//                    trigger,
//                    reaction
//                )
//            )
//            else -> throw Exception("Using invalid state machine")
//        }
    }


    private data class Observation<State>(
        var previousTrigger: Any,
        val trigger: State.() -> Any,
        val reaction: State.() -> Unit
    )
}