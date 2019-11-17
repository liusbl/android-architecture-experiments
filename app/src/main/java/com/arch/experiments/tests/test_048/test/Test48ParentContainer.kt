package com.arch.experiments.tests.test_048.test

import com.arch.experiments.tests.test_048.lib_core.Machine
import com.arch.experiments.tests.test_048.lib_core.MachineContainer
import com.arch.experiments.tests.test_048.lib_core.Observer
import com.arch.experiments.tests.test_048.lib_core.Pusher

class Test48ParentContainer(
    val parentButtonObserver: Observer<Unit>,
    val openChildPusher: Pusher<Unit>,
    val parentEditTextMachine: Machine<String>,
    val parentTextViewPusher: Pusher<String>
) : MachineContainer