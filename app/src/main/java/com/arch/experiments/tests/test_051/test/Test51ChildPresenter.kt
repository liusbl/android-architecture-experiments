package com.arch.experiments.tests.test_051.test

import com.arch.experiments.tests.test_051.lib_core.Machine
import com.arch.experiments.tests.test_051.lib_core.Pusher

class Test51ChildPresenter {
    fun start(
        parentEditTextMachine: Machine<String>,
        childEditTextMachineFactory: Machine.Factory<String>,
        childTextViewPusher: Pusher<String>
    ) {
        val childEditTextMachine = childEditTextMachineFactory.create("initial")
        childEditTextMachine.observe {
            childTextViewPusher.push(childTextViewPusher.state + "x")
            parentEditTextMachine.push(parentEditTextMachine.state + "2")
        }
    }
}