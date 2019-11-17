package com.arch.experiments.tests.test_058.lib_core


class CustomMachineLinker<State>(
    initialState: State,
    config: Config<State>
) {
    private val multiLinkedMachine = MultiLinkedMachine(initialState).also {
        it.attachMachine(config)
    }

    fun attachLinkedMachine(): Machine<State> {
        return multiLinkedMachine.attachMachine(false)
    }
}