package com.arch.experiments.tests.test_050.test

import com.arch.experiments.tests.test_050.lib_core.Machine
import com.arch.experiments.tests.test_050.lib_core.MachineContainer
import com.arch.experiments.tests.test_050.lib_core.Pusher

// TODO shoudl expose StateMachineLinker when wanting to interact more
class Test50ChildContainer(
    val childEditTextMachine: Machine<String>,
    val childTextViewPusherFactory: Pusher.Factory<String>
) : MachineContainer