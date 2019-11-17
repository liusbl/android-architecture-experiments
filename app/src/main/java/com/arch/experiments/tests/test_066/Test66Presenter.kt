package com.arch.experiments.tests.test_066

import com.arch.experiments.tests.test_066.lib.Machine

// PTR: With this architecture, whether observe methods are called
//  is decided outside the scope of presenter. Is this okay?
class Test66Presenter {
    fun start(
        textMachine1: Machine<String>,
        textMachine2: Machine<String>
    ) {
        textMachine1.observe { textMachine2.push(textMachine2.state + "X") }
        textMachine2.observe { textMachine1.push(textMachine1.state + "0") }
    }
}