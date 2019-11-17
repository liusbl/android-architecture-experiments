package com.arch.experiments.tests.test_047

import com.arch.experiments.tests.test_047.core_lib.Machine
import com.arch.experiments.tests.test_047.core_lib.MachineContainer
import com.arch.experiments.tests.test_047.core_lib.Pusher

class Test47ChildContainer(
    val childEditTextMachine: Machine<String>,
    val childTextViewPusher: Pusher<String>
) : MachineContainer