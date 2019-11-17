package com.arch.experiments.tests.test_059.lib

class PlaceholderMachineLinker<State> {
    private var isInitialized = false

    private lateinit var multiLinkedMachine: MultiLinkedMachine<State>
    private lateinit var machine: Machine<State>

    // TODO what if you want to pass initial state from presenter or something
    fun initMachine(initialState: State, config: Config<State>) {
        if (isInitialized) {
            // PTR: Pass initialState here
//            multiLinkedMachine.setConfig(machine, config)
        } else {
            // PTR: Pass current state from state machine here
            isInitialized = true
            multiLinkedMachine = MultiLinkedMachine(initialState)
            machine = multiLinkedMachine.attachMachine(config)
        }
    }

    // TODO:
//    fun detachMachine() or
}