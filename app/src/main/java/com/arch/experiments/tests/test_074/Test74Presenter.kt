package com.arch.experiments.tests.test_074

import com.arch.experiments.tests.test_074.lib.Machine

class Test74Presenter {
    fun start(
        textMachine1: Machine<String>,
        textMachine2: Machine<String>
    ) {
        textMachine1.observe { textMachine2.push(textMachine2.state + "X") }
        textMachine2.observe { textMachine1.push(textMachine1.state + "0") }
    }
}