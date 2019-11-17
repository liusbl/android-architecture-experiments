package com.arch.experiments.tests.test_052.lib_core

import com.arch.experiments.tests.test_052.lib_android.StateHandlerFactory

// TODO for now unused, couldn't make it work with switching machines
internal class CrossLinkedMachine<State>(
    private val callObserveOnInit: Boolean,
    initialState: State
) {
    private lateinit var machine1: Machine<State>
    private lateinit var machine2: Machine<State>
    private val machine1ChangeListeners = mutableListOf<(State) -> Unit>()
    private val machine2ChangeListeners = mutableListOf<(State) -> Unit>()
    private var currentState: State = initialState

    fun initCrossLinkedMachines() = attachMachine() to attachMachine()

    // TODO disable cross link when creating
    fun createMultiLinkedMachine(): MultiLinkedMachine<State> {
        val linkedMachine = MultiLinkedMachine(
//            callObserveOnInit
            currentState
//            machine1,
//            machine2,
//            machine1ChangeListeners.toList(),
//            machine2ChangeListeners.toList()
        )
        machine1ChangeListeners.clear()
        machine2ChangeListeners.clear()
        return linkedMachine
    }

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

    fun attachMachine(): Machine<State> {
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
            else -> throw Exception("#attachLinkedMachine() for more than 2 machines not supported, use MultiLinkedMachine")
        }
        return machine
    }

    fun attachMachineViaFactory(factory: StateHandlerFactory<State>): Machine<State> {
        return attachMachine().apply {
            observe { factory.onObserve.invoke(it) }
            factory.onPush { push(it) }
        }
    }
}