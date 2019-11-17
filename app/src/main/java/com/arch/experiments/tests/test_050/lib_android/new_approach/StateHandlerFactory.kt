package com.arch.experiments.tests.test_050.lib_android.new_approach

import com.arch.experiments.tests.test_050.lib_core.*

class StateHandlerFactory<T>(
    val onObserve: (T) -> Unit = { },
    val onPush: (push: (T) -> Unit) -> Unit = { }
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
        // TODO Expose global machine so you could attach more things
        val linker = MachineLinker(true, initialState)
        val presenterMachine = linker.attachLinkedMachine()
        val viewMachine = linker.attachLinkedMachine()

        viewMachine.apply {
            observe { onObserve(state) }
            onPush { push(state) } // TODO Check if it works
        }

        return presenterMachine
    }
}
