package com.arch.experiments.tests.test_061.lib

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
                        listMachine.config.onObserve(newState)
                    }
                }
            }

            override fun observe(onChanged: (State) -> Unit) {
                // PTR: NOT UNIT TESTED!
                // TODO: Works but needs to be testedd
                if (config.notifyObserverOnInit) {
//                    this.config.onObserve(state) // TBD, also this should be State/Event defined
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

        // TODO: Works but needs to be testedd
        if (config.notifyObserverOnInit) {
            config.onObserve(currentState) // TBD, also this should be State/Event defined
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
            config.onObserve(currentState) // TODO needs TESTING
        }
    }

    private abstract class MachineWithListeners<State>(var config: Config<State>) : Machine<State> {
        var onChangedListeners: MutableList<(State) -> Unit> = mutableListOf()
    }
}