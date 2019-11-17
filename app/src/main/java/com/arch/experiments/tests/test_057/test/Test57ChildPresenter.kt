package com.arch.experiments.tests.test_057.test

import com.arch.experiments.tests.test_057.lib_core.Machine

class Test57ChildPresenter {
    fun start(
        childEditTextMachine: Machine<String>,
        parentEditTextMachine: Machine<String>
    ) {
        parentEditTextMachine.observe {
            childEditTextMachine.push(childEditTextMachine.state + "x")
        }
    }
}