package com.arch.experiments.tests.test_048.test

class Test48ChildPresenter(
    private val parentContainer: Test48ParentContainer
) {
    fun start(container: Test48ChildContainer) {
        container.apply {
            childEditTextMachine.observe {
                parentContainer.parentEditTextMachine.push(
                    parentContainer.parentEditTextMachine.value + "*"
                )
                parentContainer.parentTextViewPusher.push(
                    parentContainer.parentTextViewPusher.value + "+"
                )
                childTextViewPusher.push(childTextViewPusher.value + "1")
            }
        }
    }
}