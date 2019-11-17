package com.arch.experiments.tests.test_048.lib_core

interface ContainerProvider<T: MachineContainer>  {
    fun getContainer(): T?
}