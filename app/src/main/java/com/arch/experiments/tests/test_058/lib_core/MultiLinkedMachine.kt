package com.arch.experiments.tests.test_058.lib_core

internal class MultiLinkedMachine<State>(initialState: State) {
    private val machineChangeListenerMap:
            MutableMap<Machine<State>, List<(State) -> Unit>> = mutableMapOf()
    private val machineChangeConfigMap: MutableMap<Machine<State>, Boolean> = mutableMapOf()
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
        val callObserveOnInit = machineChangeConfigMap[machine]
            ?: throw Exception("Cannot observe on not attached machine")
        if (callObserveOnInit) {
            onChanged(currentState)
        }

        val list = machineChangeListenerMap[machine]?.toMutableList()
        list?.add(onChanged)
        machineChangeListenerMap[machine] = list ?: listOf(onChanged)
    }

    fun attachMachine(callObserveOnInit: Boolean): Machine<State> {
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
        machineChangeConfigMap[machine] = callObserveOnInit
        return machine
    }

    fun attachMachine(config: Config<State>): Machine<State> {
        return attachMachine(config.callObserveOnInit).apply {
            observe { config.onObserve.invoke(it) }
            config.onPush { push(it) }
        }
    }
}