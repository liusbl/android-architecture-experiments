package com.arch.experiments.tests.test_059.lib


class CustomMachineLinker<State>(
    initialState: State,
    config: Config<State>
) {
    private val multiLinkedMachine = MultiLinkedMachine(initialState).also {
        it.attachMachine(config)
    }

//    fun attachLinkedMachine(): Machine<State> {
//        return multiLinkedMachine.attachMachine(false)
//    }
}