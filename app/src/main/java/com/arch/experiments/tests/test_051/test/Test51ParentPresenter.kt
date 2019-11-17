package com.arch.experiments.tests.test_051.test

import com.arch.experiments.tests.test_051.lib_core.Machine
import com.arch.experiments.tests.test_051.lib_core.Pusher

class Test51ParentPresenter {
    fun start(
        parentEditTextMachine: Machine<String>,
        parentTextViewPusher: Pusher<String>
    ) {
        parentEditTextMachine.observe {
            parentTextViewPusher.push(parentTextViewPusher.state + "Z")
        }
    }
}