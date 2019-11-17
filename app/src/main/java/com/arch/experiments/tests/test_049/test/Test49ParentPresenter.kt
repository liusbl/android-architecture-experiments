package com.arch.experiments.tests.test_049.test

import com.arch.experiments.tests.test_049.lib_core.ContainerProvider

class Test49ParentPresenter(
    private val childProvider: ContainerProvider<Test49ChildContainer>
) {
    fun start(container: Test49ParentContainer) {
        container.apply {
            parentButtonObserver.observe {
                openChildPusher.push()

                // BAD doesn't work
                childProvider.getContainer()?.childEditTextMachine?.push("asd")
            }
            parentEditTextMachine.observe {
                childProvider.getContainer()?.apply {
                    childEditTextMachine.push(childEditTextMachine.state + "x")
//                    childTextViewPusher.push(childTextViewPusher.value + "y")
                    // TODO hmmm You should really not have a factory here...
                    //  Since it should be initialized.. I don't know...
                }
            }
        }
    }
}