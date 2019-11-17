package com.arch.experiments.tests.test_054.lib_core

interface Config<State> {
    val onObserve: (State) -> Unit
    val onPush: (push: (State) -> Unit) -> Unit
}