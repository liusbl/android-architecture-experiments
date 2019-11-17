package com.arch.experiments.tests.test_047.core_lib

// BAD not flexible, should be able to attach any machine easily somehow

interface MachineHandler<Container : MachineContainer, ContainerContainer : ChildContainerContainer> {
    //    fun start(onCreate: (Container.() -> Unit) -> Unit, parentContainer: Parent)
    fun start(container: Container, childContainerContainer: ContainerContainer)
}

interface ChildMachineHandler<Container : MachineContainer, Parent : MachineContainer> {
    //    fun start(onCreate: (Container.() -> Unit) -> Unit, parentContainer: Parent)
    fun start(container: Container, parentContainer: Parent)
}
