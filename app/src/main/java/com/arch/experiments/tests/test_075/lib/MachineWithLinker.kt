package com.arch.experiments.tests.test_075.lib

class MachineWithLinker<State>(
    val linker: MachineLinker<State>,
    val viewMachine: Machine<State>,
    val presenterMachine: Machine<State>
) {
    fun setViewConfig(config: Config<State>) {
        linker.setConfig(viewMachine, config)
    }

    fun setPresenterConfig(config: Config<State>) {
        linker.setConfig(presenterMachine, config)
    }

    fun clearViewConfig() {
        linker.setConfig(viewMachine, StateConfig())
    }

    fun clearPresenterConfig() {
        linker.setConfig(presenterMachine, StateConfig())
    }

    companion object {
        fun <State> create(initialState: State): MachineWithLinker<State> {
            val linker = MachineLinker(initialState)
            return MachineWithLinker(
                linker,
                linker.attachMachine(StateConfig()),
                linker.attachMachine(StateConfig())
            )
        }
    }
}