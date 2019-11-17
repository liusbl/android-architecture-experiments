package com.arch.experiments.tests.test_049.lib_core

// TODO Optimize for only two machines, as in:
//  Should create by default for two machines,
//  and if "addMachine" is called,
//  then adjust accordingly:
// fun addMachine(machine: Machine<State>) {
//      // Handle
// }

// TBD for method #observe(): should onChanged:
//  1. Add to list of observations
//  2. Replace onChanged
class MachineLinker<State>(
    private val callObserveOnInit: Boolean,
    initialState: State
) {
    private lateinit var machine1: Machine<State>
    private lateinit var machine2: Machine<State>
    private val machine1ChangeListeners = mutableListOf<(State) -> Unit>()
    private val machine2ChangeListeners = mutableListOf<(State) -> Unit>()
    private var currentState: State = initialState

    fun push(machine: Machine<State>, newState: State) {
        currentState = newState
        when (machine) {
            machine1 -> machine2ChangeListeners.forEach { onChanged -> onChanged(currentState) }
            machine2 -> machine1ChangeListeners.forEach { onChanged -> onChanged(currentState) }
            else -> throw Exception("#push() for more than 2 machines not supported FOR NOW")
        }
    }

    fun observe(machine: Machine<State>, onChanged: (State) -> Unit) {
        if (callObserveOnInit) {
            onChanged(currentState)
        }
        when (machine) {
            machine1 -> machine1ChangeListeners.add(onChanged)
            machine2 -> machine2ChangeListeners.add(onChanged)
            else -> throw Exception("#observe() for more than 2 machines not supported FOR NOW")
        }
    }

    fun createLinkedMachine(): Machine<State> {
        val machine = object : Machine<State> {
            override val state: State
                get() = currentState

            override fun push(newState: State) {
                push(this, newState)
            }

            override fun observe(onChanged: (State) -> Unit) {
                observe(this, onChanged)
            }
        }
        when {
            !::machine1.isInitialized -> machine1 = machine
            !::machine2.isInitialized -> machine2 = machine
            else -> throw Exception("#attachLinkedMachine() for more than 2 machines not supported FOR NOW")
        }
        return machine
    }
}