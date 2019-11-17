package com.arch.experiments.tests.test_058.test

import com.arch.experiments.tests.test_058.lib_core.Machine
import com.arch.experiments.tests.test_058.lib_core.Observer
import com.arch.experiments.tests.test_058.lib_core.Pusher

class Test58ParentPresenter {
    fun start(
        showChildButtonObserver: Observer<Unit>,
        showChildPusher: Pusher<Unit>,
        parentEditTextMachine: Machine<String>,
        childEditTextMachine: Machine<String>
    ) {
        showChildButtonObserver.observe { showChildPusher.push() }
        childEditTextMachine.observe {
            parentEditTextMachine.push(parentEditTextMachine.state + "0")
        }
    }
}