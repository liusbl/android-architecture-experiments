package com.arch.experiments.tests.test_047.core_lib

interface MachineInitializer {
    fun initMachines(): MachineContainer

    fun onCreate(onCreate: MachineContainer.() -> Unit)
}
