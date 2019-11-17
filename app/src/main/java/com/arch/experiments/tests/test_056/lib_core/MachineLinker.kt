package com.arch.experiments.tests.test_056.lib_core


class MachineLinker<State>(
    initialState: State
) {
    private val multiLinkedMachine by lazy {
        MultiLinkedMachine<State>(initialState)
    }

    fun attachLinkedMachine(callObserveOnInit: Boolean): Machine<State> {
        return multiLinkedMachine.attachMachine(callObserveOnInit)
    }

//    fun attachMachineViaFactory(factory: StateHandlerFactory<State>) {
//        multiLinkedMachine.attachMachineViaFactory(factory)
//    }

//    fun createLinkedMachine(factory: StateHandlerFactory<State>): Machine<State> {
//        multiLinkedMachine.attachMachineViaFactory(factory)
//        return attachLinkedMachine()
//    }
}