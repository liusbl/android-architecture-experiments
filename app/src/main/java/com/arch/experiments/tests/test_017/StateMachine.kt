package com.arch.experiments.tests.test_017

interface StateMachine<State> : StateSupplier<State>, StateObserver<State>