package com.arch.experiments.tests.test_059.lib

internal class MultiLinkedMachine<State>(initialState: State) {
    private var currentState: State = initialState

    private val machineListenerMap = mutableMapOf<Machine<State>, CancellableConfig<State>>()

    private fun push(machine: Machine<State>, newState: State) {
        machineListenerMap.forEach { machineListenerEntry ->
            if (machineListenerEntry.key !== machine) {
                val config = machineListenerEntry.value
                config.onObserve(newState)
            }
        }
    }

    private fun observe(machine: Machine<State>, onChanged: (State) -> Unit) {
        val config = machineListenerMap[machine]
            ?: throw Exception("Observing machine that wasn't attached")
        if (config.callObserveOnInit) {
            onChanged(currentState)
        }
        val newConfig = object : Config<State> {
            override val onObserve: (State) -> Unit = { state: State ->
                config.onObserve(state)
                onChanged(state)
            }
            override val onPush: (push: (State) -> Unit) -> Unit = config.onPush
            override val callObserveOnInit: Boolean = config.callObserveOnInit
        }
        machineListenerMap[machine] = CancellableConfig(newConfig, true)
    }

    fun attachMachine(config: Config<State>): Machine<State> {
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
        val cancellableConfig = CancellableConfig(config, true)
        machineListenerMap[machine] = cancellableConfig

        if (cancellableConfig.callObserveOnInit) {
            cancellableConfig.onObserve(currentState) // TBD is this correct?
        }
        cancellableConfig.onPush { state ->
            if (cancellableConfig.isValid) {
                push(machine, state)
            }
        }

        return machine
    }

    fun setConfig(machine: Machine<State>, config: Config<State>) {
        val oldConfig = machineListenerMap[machine]!!
        oldConfig.isValid = false

        val cancellableConfig = CancellableConfig(config, true)
        machineListenerMap[machine] = cancellableConfig

        if (cancellableConfig.callObserveOnInit) {
            cancellableConfig.onObserve(currentState) // TBD is this correct?
        }
        cancellableConfig.onPush { state ->
            if (cancellableConfig.isValid) {
                push(machine, state)
            }
        }
    }

    private class CancellableConfig<State>(
        private var config: Config<State>,
        var isValid: Boolean
    ) : Config<State> {
        override val onObserve: (State) -> Unit get() = config.onObserve
        override val onPush: (push: (State) -> Unit) -> Unit get() = config.onPush
        override val callObserveOnInit: Boolean get() = config.callObserveOnInit
    }
}