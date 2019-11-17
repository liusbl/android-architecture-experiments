package com.arch.experiments.tests.test_063.lib


open class EventConfig<State>(
    override val onObserve: (state: State) -> Unit = {},
    override val onPush: (pushState: (State) -> Unit) -> Unit = {}
) : EmptyConfig<State>() {
    override val notifyObserverOnInit = false
}

open class StateConfig<State>(
    override val onObserve: (state: State) -> Unit = {},
    override val onPush: (pushState: (State) -> Unit) -> Unit = {}
) : EmptyConfig<State>() {
    override val notifyObserverOnInit = true
}

abstract class EmptyConfig<State>(
    override val onObserve: (State) -> Unit = {},
    override val onPush: (push: (State) -> Unit) -> Unit = {}
) : Config<State> {
    override val onObserveTransform: (state: State) -> Unit = { state ->
    }
    override val onPushTransform: (state: State) -> Unit = { state ->
    }
}

interface Config<State> {
    val onObserve: (State) -> Unit
    val onPush: (push: (State) -> Unit) -> Unit
    val onObserveTransform: (state: State) -> Unit
    val onPushTransform: (state: State) -> Unit
    val notifyObserverOnInit: Boolean
}