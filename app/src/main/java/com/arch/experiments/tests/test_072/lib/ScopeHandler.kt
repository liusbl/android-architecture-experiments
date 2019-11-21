package com.arch.experiments.tests.test_072.lib

class ScopeHandler {
    private val machineList = mutableListOf<LinkedMachines<Any>>()

    fun clearConfigs() {
        machineList.forEach { machine ->
            machine.apply {
                linker.clearConfig(viewMachine)
                linker.clearConfig(presenterMachine)
            }
        }
    }

    fun <State> addLinkedMachines(
        machineLinker: MachineLinker<State>,
        machine1: Machine<State>,
        machine2: Machine<State>
    ) {
        machineList.add(LinkedMachines(machineLinker, machine1, machine2) as LinkedMachines<Any>)
    }

    class LinkedMachines<State>(
        val linker: MachineLinker<State>,
        val viewMachine: Machine<State>,
        val presenterMachine: Machine<State>
    )
}