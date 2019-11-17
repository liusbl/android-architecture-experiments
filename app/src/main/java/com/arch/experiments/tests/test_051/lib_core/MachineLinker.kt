package com.arch.experiments.tests.test_051.lib_core

import com.arch.experiments.tests.test_051.lib_android.StateHandlerFactory


class MachineLinker<State>(
    private val callObserveOnInit: Boolean,
    initialState: State
) {
    // TODO uncomment and implement with 2 machines.
    //  I couldn't make it work previously

//    private val crossLinkedMachine =
//        CrossLinkedMachine(callObserveOnInit, initialState) // TODO How to dereference
//    private val multiLinkedMachine by lazy { crossLinkedMachine.createMultiLinkedMachine() }
//    private var machineCount = 0
//
//    fun initCrossLinkedMachines(): Pair<Machine<State>, Machine<State>> {
//        machineCount = 2
//        // TODO, check if machine != crossLinkerMachine and notify the user,
//        //  since you should init twice. OR prevent it some other way
//        return crossLinkedMachine.initCrossLinkedMachines()
//    }
//
//    // TODO consider removal of machines
//    fun attachLinkedMachine(): Machine<State> {
//        machineCount++
//        return if (machineCount > 2) {
//            multiLinkedMachine.attachMachine()
//        } else {
//            crossLinkedMachine.attachMachine()
//        }
//    }
//
//    fun attachMachineViaFactory(factory: StateHandlerFactory<State>) {
//        machineCount++
//        if (machineCount > 2) {
//            multiLinkedMachine.attachMachineViaFactory(factory)
//        } else {
//            crossLinkedMachine.attachMachineViaFactory(factory)
//        }
//    }

    private val multiLinkedMachine by lazy {
        MultiLinkedMachine<State>(
            callObserveOnInit,
            initialState
        )
    }

    fun initCrossLinkedMachines(): Pair<Machine<State>, Machine<State>> {
        return multiLinkedMachine.attachMachine() to multiLinkedMachine.attachMachine()
    }

    fun attachLinkedMachine(): Machine<State> {
        return multiLinkedMachine.attachMachine()
    }

    fun attachMachineViaFactory(factory: StateHandlerFactory<State>) {
        multiLinkedMachine.attachMachineViaFactory(factory)
    }

    fun createLinkedMachine(factory: StateHandlerFactory<State>): Machine<State> {
        multiLinkedMachine.attachMachineViaFactory(factory)
        return attachLinkedMachine()
    }
}