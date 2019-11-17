package com.arch.experiments.tests.test_047

import com.arch.experiments.tests.test_047.core_lib.ChildMachineHandler

class Test47ChildPresenter :
    ChildMachineHandler<Test47ChildContainer, Test47ParentContainer> {
    override fun start(
//        onCreate: (Iteration5FragmentContainer.() -> Unit) -> Unit,
        container: Test47ChildContainer,
        parentContainer: Test47ParentContainer
    ) {
        container.childEditTextMachine.observe {
            parentContainer.parentEditTextMachine.push(parentContainer.parentEditTextMachine.value + "*")
            parentContainer.parentTextViewPusher.push(parentContainer.parentTextViewPusher.value + "+")
            container.childTextViewPusher.push(container.childTextViewPusher.value + "1")
        }
    }
}