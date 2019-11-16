package com.arch.experiments.tests.test_019.lib

interface StateMachine<State> : StateSupplier<State>, StateObserver<State, Unit> {
    fun start(stateMachine: GlobalStateMachine<State>)
}