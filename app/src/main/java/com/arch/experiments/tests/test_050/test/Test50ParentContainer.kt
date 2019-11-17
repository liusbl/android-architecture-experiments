package com.arch.experiments.tests.test_050.test

import com.arch.experiments.tests.test_050.lib_core.Machine
import com.arch.experiments.tests.test_050.lib_core.MachineContainer
import com.arch.experiments.tests.test_050.lib_core.Observer
import com.arch.experiments.tests.test_050.lib_core.Pusher

// TODO shoudl expose StateMachineLinker when wanting to interact more
class Test50ParentContainer(
    val parentButtonObserver: Observer<Unit>,
    val openChildPusher: Pusher<String>, // Or maybe pass factory?
    val parentEditTextMachine: Machine<String>,
    val parentTextViewPusher: Pusher<String>
) : MachineContainer