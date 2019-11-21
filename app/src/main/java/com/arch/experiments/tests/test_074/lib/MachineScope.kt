package com.arch.experiments.tests.test_074.lib

class MachineScope {
    val machineList = mutableListOf<LinkedMachines<Any>>()

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

//    fun <State> clearConfigs(
//        machineLinker: MachineLinker<State>
//    ) {
//        machineLinker.clearConfigs()
//    }

    class LinkedMachines<State>(
        val linker: MachineLinker<State>,
        val viewMachine: Machine<State>,
        val presenterMachine: Machine<State>
    )
}