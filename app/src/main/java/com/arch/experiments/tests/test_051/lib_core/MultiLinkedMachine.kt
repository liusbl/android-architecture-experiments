package com.arch.experiments.tests.test_051.lib_core

import com.arch.experiments.tests.test_051.lib_android.StateHandlerFactory

internal class MultiLinkedMachine<State>(
    private val callObserveOnInit: Boolean,
    initialState: State
//    machine1: Machine<State>,
//    machine2: Machine<State>,
//    machine1ChangeListeners: List<(State) -> Unit>,
//    machine2ChangeListeners: List<(State) -> Unit>
) {
    // TODO uncomment and implement with 2 machines.
    //  I couldn't make it work previously

    //    private val machineList: MutableList<Machine<State>> = mutableListOf(machine1, machine2)
//    private val machineChangeListenerMap: MutableMap<Machine<State>, List<(State) -> Unit>> =
//        mutableMapOf(
//            machine1 to machine1ChangeListeners,
//            machine2 to machine2ChangeListeners
//        )
    private val machineList: MutableList<Machine<State>> = mutableListOf()
    private val machineChangeListenerMap: MutableMap<Machine<State>, List<(State) -> Unit>> =
        mutableMapOf()
    private var currentState: State = initialState

    fun push(machine: Machine<State>, newState: State) {
        currentState = newState
        machineChangeListenerMap.forEach { (mapMachine: Machine<State>, onChangedListeners: List<(State) -> Unit>) ->
            if (mapMachine != machine) {
                onChangedListeners.forEach { onChanged -> onChanged(currentState) }
            }
        }
    }

    fun observe(machine: Machine<State>, onChanged: (State) -> Unit) {
        if (callObserveOnInit) {
            onChanged(currentState)
        }

        val list = machineChangeListenerMap[machine]?.toMutableList()
        list?.add(onChanged)
        machineChangeListenerMap[machine] = list ?: listOf(onChanged)
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
        machineList.add(machine)
        return machine
    }

    fun attachMachineViaFactory(factory: StateHandlerFactory<State>) =
        attachMachine().apply {
            observe { factory.onObserve.invoke(it) }
            factory.onPush { push(it) }
        }
}