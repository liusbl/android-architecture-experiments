package com.arch.experiments.tests.test_053.test

import com.arch.experiments.tests.test_053.lib_core.Machine
import com.arch.experiments.tests.test_053.lib_core.Observer
import com.arch.experiments.tests.test_053.lib_core.Pusher

class Test53Presenter {
    fun start(
        buttonObserver: Observer<Unit>,
        toastPusher: Pusher<String>,
        editText1Machine: Machine<String>,
        editText2Machine: Machine<String>
    ) {
        buttonObserver.observe {
            toastPusher.push(editText2Machine.state + editText1Machine.state)
        }
        editText1Machine.observe { editText2Machine.push(editText2Machine.state + "X") }
        editText2Machine.observe { editText1Machine.push(editText1Machine.state + "8") }
    }
}