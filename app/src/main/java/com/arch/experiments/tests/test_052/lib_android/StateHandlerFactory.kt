package com.arch.experiments.tests.test_052.lib_android

import com.arch.experiments.tests.test_052.lib_core.*

class StateHandlerFactory<T>(
    val onObserve: (T) -> Unit = { },
    val onPush: (push: (T) -> Unit) -> Unit = { },
    val isEvent: Boolean // TODO find better way to use
) {
    fun createStateProviderFactory(): StateProvider.Factory<T> {
        return object : StateProvider.Factory<T> {
            override fun create(initialState: T) = createLinkedMachine(initialState)
        }
    }

    fun createStateProvider(initialState: T): StateProvider<T> {
        return createStateProviderFactory().create(initialState)
    }

    fun createPusherFactory(): Pusher.Factory<T> {
        return object : Pusher.Factory<T> {
            override fun create(initialState: T) = createLinkedMachine(initialState)
        }
    }

    fun createPusher(initialState: T): Pusher<T> {
        return createPusherFactory().create(initialState)
    }

    fun createObserverFactory(): Observer.Factory<T> {
        return object : Observer.Factory<T> {
            override fun create(initialState: T) = createLinkedMachine(initialState)
        }
    }

    fun createObserver(initialState: T): Observer<T> {
        return createObserverFactory().create(initialState)
    }

    fun createMachineFactory(): Machine.Factory<T> {
        return object : Machine.Factory<T> {
            override fun create(initialState: T) = createLinkedMachine(initialState)
        }
    }

    fun createMachine(initialState: T): Machine<T> {
        return createMachineFactory().create(initialState)
    }

    private fun createLinkedMachine(initialState: T): Machine<T> {
        val linker = MachineLinker(initialState)
        val presenterMachine = linker.attachLinkedMachine(false)
        val viewMachine = linker.attachLinkedMachine(true)

        viewMachine.observe { onObserve.invoke(it) }
        onPush { viewMachine.push(it) }

        return presenterMachine
    }
}
