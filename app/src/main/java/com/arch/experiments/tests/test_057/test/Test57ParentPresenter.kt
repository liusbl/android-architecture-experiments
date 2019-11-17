package com.arch.experiments.tests.test_057.test

import com.arch.experiments.tests.test_057.lib_core.Machine
import com.arch.experiments.tests.test_057.lib_core.Observer
import com.arch.experiments.tests.test_057.lib_core.Pusher
import timber.log.Timber

class Test57ParentPresenter {
    fun start(
        showChildButtonObserver: Observer<Unit>,
        showChildPusher: Pusher<Unit>,
        parentEditTextMachine: Machine<String>
    ) {
        showChildButtonObserver.observe { showChildPusher.push() }
        parentEditTextMachine.observe { Timber.d("TEST") }
//        parentEditTextMachine.observe {  }
    }
}