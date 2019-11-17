package com.arch.experiments.tests.test_058.lib_core

// TODO UNFINISHED!!
class UndefinedMachineLinker<State> {
    private var multiLinkedMachine: MultiLinkedMachine<State>? = null

    private var isInitialized = false
    fun initialize(initialState: State, config: Config<State>): Machine<State> {
        if (isInitialized) throw Exception("Shouldn't initialize twice")
        isInitialized = true
        multiLinkedMachine = MultiLinkedMachine(initialState).also {
            it.attachMachine(config)
        }
        return attachLinkedMachine()
    }

    fun attachLinkedMachine(): Machine<State> {
        // TODO attach empty machine at first
        if (multiLinkedMachine == null) {
            return object : Machine<State> {
                override fun push(newState: State) {
                    // Empty
                }

                override fun observe(onChanged: (State) -> Unit) {
                    // Empty
                }

                override val state: State
                    get() = throw Exception("State uninitialized")
            }
        } else {
            return multiLinkedMachine?.attachMachine(false)!!
        }
    }
}