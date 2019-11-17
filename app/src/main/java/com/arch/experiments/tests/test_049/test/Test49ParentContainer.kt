package com.arch.experiments.tests.test_049.test

import com.arch.experiments.tests.test_049.lib_core.Machine
import com.arch.experiments.tests.test_049.lib_core.MachineContainer
import com.arch.experiments.tests.test_049.lib_core.Observer
import com.arch.experiments.tests.test_049.lib_core.Pusher

// TODO should expose StateMachineLinker when wanting to interact more
class Test49ParentContainer(
    val parentButtonObserver: Observer<Unit>,
    val openChildPusher: Pusher<String>, // Or maybe pass factory?
    val parentEditTextMachine: Machine<String>,
    val parentTextViewPusher: Pusher<String>
) : MachineContainer