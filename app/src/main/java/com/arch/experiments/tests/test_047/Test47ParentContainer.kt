package com.arch.experiments.tests.test_047

import com.arch.experiments.tests.test_047.core_lib.Machine
import com.arch.experiments.tests.test_047.core_lib.MachineContainer
import com.arch.experiments.tests.test_047.core_lib.Observer
import com.arch.experiments.tests.test_047.core_lib.Pusher

class Test47ParentContainer(
    val openChildButtonObserver: Observer<Unit>,
    val openChildPusher: Pusher<Unit>,
    val parentEditTextMachine: Machine<String>,
    val parentTextViewPusher: Pusher<String>
) : MachineContainer