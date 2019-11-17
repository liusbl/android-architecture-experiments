package com.arch.experiments.tests.test_058.test

import com.arch.experiments.tests.test_058.lib_core.Machine

class Test58ChildPresenter {
    fun start(
        childEditTextMachine: Machine<String>,
        parentEditTextMachine: Machine<String>
    ) {
        parentEditTextMachine.observe {
            childEditTextMachine.push(childEditTextMachine.state + "x")
        }
    }
}