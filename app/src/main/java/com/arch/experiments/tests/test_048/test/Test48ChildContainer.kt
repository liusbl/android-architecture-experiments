package com.arch.experiments.tests.test_048.test

import com.arch.experiments.tests.test_048.lib_core.Machine
import com.arch.experiments.tests.test_048.lib_core.MachineContainer
import com.arch.experiments.tests.test_048.lib_core.Pusher

class Test48ChildContainer(
    val childEditTextMachine: Machine<String>,
    val childTextViewPusher: Pusher<String>
) : MachineContainer