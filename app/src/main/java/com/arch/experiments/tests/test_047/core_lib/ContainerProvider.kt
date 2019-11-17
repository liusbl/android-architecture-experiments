package com.arch.experiments.tests.test_047.core_lib

interface ContainerProvider<Container: MachineContainer> {
    fun getContainer(): Container
}