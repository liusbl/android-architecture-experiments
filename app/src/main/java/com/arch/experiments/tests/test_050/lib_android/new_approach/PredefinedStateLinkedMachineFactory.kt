package com.arch.experiments.tests.test_050.lib_android.new_approach

import com.arch.experiments.tests.test_050.lib_core.MachineLinker

// PTR: I want to be able to create and add machines, but in a way that couldn't be confused

// PTR: You should be able to create with both factories and non facotories,
//  but add with only non-factories
class PredefinedStateLinkedMachineFactory<T>(
    initialState: T,
    onObserve: (T) -> Unit = { },
    onPush: (push: (T) -> Unit) -> Unit = { }
) {
    private val linker: MachineLinker<T> = MachineLinker(true, initialState)

    init {
        val presenterMachine = linker.attachLinkedMachine()
        val viewMachine = linker.attachLinkedMachine()

        viewMachine.apply {
            observe { onObserve(state) }
            onPush { push(state) }
        }
    }
}