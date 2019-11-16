package com.arch.experiments.tests.test_026.core_lib

// TODO Optimize for only two machines, as in:
//  Should create by default for two machines,
//  and if "addMachine" is called,
//  then adjust accordingly
class GlobalMachine<State>(
//    private val machine1: Machine<State>,
//    private val machine2: Machine<State>,
    private val callObserveOnInit: Boolean,
    initialState: State
) {
    private val stateObservations =
        mutableSetOf<StateObservation<State>>() // TODO convert to map for faster search
    var state: State = initialState
        private set
    var action: Action = Action.INACTIVE

    fun push(machine: Machine<State>, update: () -> State) {
        state = update()
        stateObservations.filterNot { observation -> observation.machine === machine }
            .forEach { observation -> observation.onChanged() }
    }

    fun observe(machine: Machine<State>, onChanged: () -> Unit) {
        if (callObserveOnInit) {
            onChanged() // PTR: if we want to  not notify observers on initial state then comment out this line
        }
        stateObservations.add(StateObservation(machine, onChanged))
    }

    fun pushToggle(machine: ToggleMachine<State>, update: () -> State) {
//        state = update()
//        stateObservations.filterNot { observation -> observation.machine === machine }
//            .forEach { observation -> observation.onChanged() }
    }

    fun observeToggle(machine: ToggleMachine<State>, onChanged: () -> Unit) {
//        onChanged() // PTR: if we want to  not notify observers on initial state then comment out this line
//        stateObservations.add(StateObservation(machine, onChanged))
    }

    fun createMachine() = object : Machine<State> {
        override val value: State
            get() = state

        override fun push(update: () -> State) {
            this@GlobalMachine.push(this, update)
        }

        override fun observe(onChanged: () -> Unit) {
            this@GlobalMachine.observe(this, onChanged)
        }
    }

    fun createToggleMachine() = object : ToggleMachine<State> {
        override val value: State
            get() = state

        override fun push(update: () -> State) {
            this@GlobalMachine.pushToggle(this, update)
        }

        override fun observe(onChanged: () -> Unit) {
            this@GlobalMachine.observeToggle(this, onChanged)
        }
    }

    private data class StateObservation<State>(
        val machine: Machine<State>,
        val onChanged: () -> Unit
    )
}