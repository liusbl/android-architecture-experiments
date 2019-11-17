package com.arch.experiments.tests.test_049.lib_core

interface ContainerProvider<T: MachineContainer>  {
    fun getContainer(): T?
}