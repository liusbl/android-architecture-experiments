package com.arch.experiments.tests.test_047

import com.arch.experiments.tests.test_047.core_lib.MachineHandler

class Test47ParentPresenter : MachineHandler<Test47ParentContainer, Test47ChildContainerContainer> {
    // TODO: Boilerplate to having to write "container" everywhere

    override fun start(
        container: Test47ParentContainer,
        childContainerContainer: Test47ChildContainerContainer
    ) {
        container.openChildButtonObserver.observe {
            // PTR: Should not work but also shouldn't crash
            childContainerContainer.childContainer1Provider?.getContainer()
                ?.childEditTextMachine?.push("asd")

            container.openChildPusher.push()
        }
        container.parentEditTextMachine.observe {
            childContainerContainer.childContainer1Provider?.getContainer()?.apply {
                childEditTextMachine.push(childEditTextMachine.value + "x")
                childTextViewPusher.push(childTextViewPusher.value + "y")
            }
        }
    }
}