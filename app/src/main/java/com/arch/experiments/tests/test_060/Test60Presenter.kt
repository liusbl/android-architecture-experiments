package com.arch.experiments.tests.test_060

import com.arch.experiments.tests.test_060.lib.Machine

class Test60Presenter {
    fun start(
        editText1Machine: Machine<String>,
        editText2Machine: Machine<String>
    ) {
        editText1Machine.observe {
            editText2Machine.push(editText2Machine.state + "X")
        }
        editText2Machine.observe {
            editText1Machine.push(editText1Machine.state + "0")
        }
    }
}