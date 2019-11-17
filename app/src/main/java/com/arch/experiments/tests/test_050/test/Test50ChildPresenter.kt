package com.arch.experiments.tests.test_050.test

import com.arch.experiments.tests.test_050.lib_core.Machine
import com.arch.experiments.tests.test_050.lib_core.Pusher
import com.arch.experiments.tests.test_050.misc.StringProvider

class Test50ChildPresenter(
    private val stringProvider: StringProvider,
    private val parentEditTextMachine: Machine<String>, // PTR come from parent
    private val parentTextViewPusher: Pusher<String> // PTR come from parent
) {
    fun start(container: Test50ChildContainer) {
        container.apply {
            val childTextViewPusher =
                childTextViewPusherFactory.create(stringProvider.getInitialResult())
            childEditTextMachine.observe {
                parentEditTextMachine.push(parentEditTextMachine.state + "*")
                parentTextViewPusher.push(parentTextViewPusher.state + "+")
                childTextViewPusher.push(childTextViewPusher.state + "1")
            }
        }
    }
}