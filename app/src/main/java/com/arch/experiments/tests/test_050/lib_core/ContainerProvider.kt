package com.arch.experiments.tests.test_050.lib_core

interface ContainerProvider<T: MachineContainer>  {
    fun getContainer(): T?
}