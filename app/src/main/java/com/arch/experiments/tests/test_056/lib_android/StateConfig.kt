package com.arch.experiments.tests.test_056.lib_android

import com.arch.experiments.tests.test_056.lib_core.*

class StateConfig<T>(
    override val onObserve: (T) -> Unit = { },
    override val onPush: (push: (T) -> Unit) -> Unit = { }
) : Config<T> {
    fun createStateProviderFactory() = object : StateProvider.Factory<T> {
        override fun create(initialState: T) = createLinkedMachine(initialState)
    }

    fun createStateProvider(initialState: T) = createStateProviderFactory().create(initialState)

    fun createPusherFactory() = object : Pusher.Factory<T> {
        override fun create(initialState: T) = createLinkedMachine(initialState)
    }

    fun createPusher(initialState: T) = createPusherFactory().create(initialState)

    fun createObserverFactory() = object : Observer.Factory<T> {
        override fun create(initialState: T) = createLinkedMachine(initialState)
    }

    fun createObserver(initialState: T) = createObserverFactory().create(initialState)

    fun createMachineFactory() = object : Machine.Factory<T> {
        override fun create(initialState: T) = createLinkedMachine(initialState)
    }

    fun createMachine(initialState: T) = createMachineFactory().create(initialState)

    private fun createLinkedMachine(initialState: T): Machine<T> {
        val linker = MachineLinker(initialState)
        val presenterMachine = linker.attachLinkedMachine(false)
        val viewMachine = linker.attachLinkedMachine(true)

        viewMachine.observe { onObserve.invoke(it) }
        onPush { viewMachine.push(it) }

        return presenterMachine
    }
}
