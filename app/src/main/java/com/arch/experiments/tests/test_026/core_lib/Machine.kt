package com.arch.experiments.tests.test_026.core_lib

// TODO Maybe should have id?
interface Machine<T> : Pusher<T>, Observer<T>


interface Pusher<T> : ValueProvider<T> {
    fun push(update: () -> T = { value }) // TBD: Is the default value needed?
}

interface Observer<T> : ValueProvider<T> {
    fun observe(onChanged: () -> Unit)
}

interface ValueProvider<T> {
    val value: T
}

interface ToggleMachine<T> : TogglePusher<T>, ToggleObserver<T>

interface TogglePusher<T> : ValueProvider<T> {
    fun push(update: () -> T = { value })
}

interface ToggleObserver<T> : ValueProvider<T> {
    fun observe(onChanged: () -> Unit)
}

// TODO
//
//
//
//
//
// TODO experiment with this
interface A : () -> Unit, (String) -> Unit {
    override fun invoke() {
        // Empty
    }

    override fun invoke(p1: String) {
        // Empty
    }
}

interface B : (String) -> String {

}

// TODO experiment with this!!!!
//interface Machine<T> : (T) -> T {
//
//}