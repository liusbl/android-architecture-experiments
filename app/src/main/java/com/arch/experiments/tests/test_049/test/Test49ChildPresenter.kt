package com.arch.experiments.tests.test_049.test

import com.arch.experiments.tests.test_049.misc.StringProvider

class Test49ChildPresenter(
    private val stringProvider: StringProvider,
    private val parentContainer: Test49ParentContainer
) {
    fun start(container: Test49ChildContainer) {
        container.apply {
            val childTextViewPusher =
                childTextViewPusherFactory.create(stringProvider.getInitialResult())
            childEditTextMachine.observe {
                parentContainer.parentEditTextMachine.push(
                    parentContainer.parentEditTextMachine.state + "*"
                )
                parentContainer.parentTextViewPusher.push(
                    parentContainer.parentTextViewPusher.state + "+"
                )
                childTextViewPusher.push(childTextViewPusher.state + "1")
            }
        }
    }
}