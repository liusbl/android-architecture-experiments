package com.arch.experiments.tests.test_059.lib


internal class MachineLinker<State>(
    initialState: State
) {
    private val multiLinkedMachine by lazy {
        MultiLinkedMachine<State>(initialState)
    }

    fun attachLinkedMachine(callObserveOnInit: Boolean): Machine<State> {
//        return multiLinkedMachine.attachMachine(callObserveOnInit)
        return TODO()
    }
}