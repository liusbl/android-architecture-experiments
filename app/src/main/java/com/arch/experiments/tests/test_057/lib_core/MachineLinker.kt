package com.arch.experiments.tests.test_057.lib_core


internal class MachineLinker<State>(
    initialState: State
) {
    private val multiLinkedMachine by lazy {
        MultiLinkedMachine<State>(initialState)
    }

    fun attachLinkedMachine(callObserveOnInit: Boolean): Machine<State> {
        return multiLinkedMachine.attachMachine(callObserveOnInit)
    }
}