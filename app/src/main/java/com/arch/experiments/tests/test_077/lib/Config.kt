package com.arch.experiments.tests.test_077.lib

class ConfigTransformationFactory<State> {
    fun create(list: List<ConfigTransformation<State>>): ConfigTransformation<State> {
        return object : ConfigTransformation<State> {
            override fun transformObserve(state: State, observe: (State) -> Unit) {
                list.fold(observe, { acc, transformation ->
                    { newState -> transformation.transformObserve(newState, acc) }
                })(state)
            }

            override fun transformOnPush(state: State, push: (State) -> Unit) {
                list.fold(push, { acc, transformation ->
                    { newState -> transformation.transformOnPush(newState, acc) }
                })(state)
            }
        }
    }
}

open class DefaultConfigTransformation<State> : ConfigTransformation<State> {
    override fun transformObserve(state: State, observe: (State) -> Unit) {
        observe(state)
    }

    override fun transformOnPush(state: State, push: (State) -> Unit) {
        push(state)
    }
}

interface ConfigTransformation<State> {
    fun transformObserve(state: State, observe: (State) -> Unit)

    fun transformOnPush(state: State, push: (State) -> Unit)
}


open class EventConfig<State>(
    override var transformation: ConfigTransformation<State> = DefaultConfigTransformation()
) : EmptyConfig<State>() {
    override val notifyObserverOnInit = false
}

open class StateConfig<State>(
    override var transformation: ConfigTransformation<State> = DefaultConfigTransformation()
) : EmptyConfig<State>() {
    override val notifyObserverOnInit = true
}

abstract class EmptyConfig<State>(
    override var transformation: ConfigTransformation<State> = DefaultConfigTransformation()
) : Config<State> {
    override fun observe(state: State) {
        // Empty
    }

    override fun onPush(push: (State) -> Unit) {
        // Empty
    }
}

interface Config<State> {
    val notifyObserverOnInit: Boolean
    val transformation: ConfigTransformation<State>

    fun observe(state: State)

    fun onPush(push: (State) -> Unit)
}