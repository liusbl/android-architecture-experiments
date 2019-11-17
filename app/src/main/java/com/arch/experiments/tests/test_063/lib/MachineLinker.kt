package com.arch.experiments.tests.test_063.lib

class MachineLinker<State>(initialState: State) {
    var currentState = initialState
        private set
    private val machineList = mutableListOf<MachineWithListeners<State>>()

    fun attachMachine(config: Config<State>): Machine<State> {
        val machine = object : MachineWithListeners<State>(config) {
            override val state: State get() = currentState

            override fun push(newState: State) {
                currentState = newState
                machineList.forEach { listMachine ->
                    if (this !== listMachine) {
                        listMachine.onChangedListeners.forEach { listener ->
                            listener.invoke(newState)
                        }
                        listMachine.config.onObserveTransform(newState)
                    }
                }
            }

            override fun observe(onChanged: (State) -> Unit) {
                if (config.notifyObserverOnInit) {
                    onChanged(state)
                }
                onChangedListeners.add(onChanged)
            }
        }
        config.onPush {
            if (machine.config == config) {
                machine.push(it)
            }
        }

        if (config.notifyObserverOnInit) {
            config.onObserveTransform(currentState)
        }

        machineList.add(machine)
        return machine
    }

    fun setConfig(machine: Machine<State>, config: Config<State>) {
        val listMachine = machineList.firstOrNull { listMachine -> listMachine == machine }!!
        listMachine.config = config
        config.onPush {
            if (listMachine.config == config) {
                listMachine.push(it)
            }
        }
        if (config.notifyObserverOnInit) {
            config.onObserveTransform(currentState)
        }
    }

    private abstract class MachineWithListeners<State>(var config: Config<State>) : Machine<State> {
        var onChangedListeners: MutableList<(State) -> Unit> = mutableListOf()
    }
}