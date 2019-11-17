package com.arch.experiments.tests.test_060.lib

open class EventConfig<State>(
    override val onObserve: (State) -> Unit = {},
    override val onPush: (push: (State) -> Unit) -> Unit = {}
) : EmptyConfig<State>() {
    override val notifyObserverOnInit = false // TODO implement
}

open class StateConfig<State>(
    override val onObserve: (State) -> Unit = {},
    override val onPush: (push: (State) -> Unit) -> Unit = {}
) : EmptyConfig<State>() {
    override val notifyObserverOnInit = true
}

abstract class EmptyConfig<State>(
    override val onObserve: (State) -> Unit = {},
    override val onPush: (push: (State) -> Unit) -> Unit = {}
) : Config<State>

interface Config<State> {
    val onObserve: (State) -> Unit
    val onPush: (push: (State) -> Unit) -> Unit
    val notifyObserverOnInit: Boolean
}