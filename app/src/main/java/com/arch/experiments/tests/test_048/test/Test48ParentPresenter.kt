package com.arch.experiments.tests.test_048.test

import com.arch.experiments.tests.test_048.lib_core.ContainerProvider

class Test48ParentPresenter(
    private val childProvider: ContainerProvider<Test48ChildContainer>
) {
    fun start(container: Test48ParentContainer) {
        container.apply {
            parentButtonObserver.observe {
                openChildPusher.push()

                // BAD doesn't work
                childProvider.getContainer()?.childEditTextMachine?.push("asd")
            }
            parentEditTextMachine.observe {
                childProvider.getContainer()?.apply {
                    childEditTextMachine.push(childEditTextMachine.value + "x")
                    childTextViewPusher.push(childTextViewPusher.value + "y")
                }
            }
        }
    }
}