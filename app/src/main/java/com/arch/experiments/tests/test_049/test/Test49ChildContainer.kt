package com.arch.experiments.tests.test_049.test

import com.arch.experiments.tests.test_049.lib_core.Machine
import com.arch.experiments.tests.test_049.lib_core.MachineContainer
import com.arch.experiments.tests.test_049.lib_core.Pusher

// TODO shoudl expose StateMachineLinker when wanting to interact more
class Test49ChildContainer(
    val childEditTextMachine: Machine<String>,
    val childTextViewPusherFactory: Pusher.Factory<String>
) : MachineContainer