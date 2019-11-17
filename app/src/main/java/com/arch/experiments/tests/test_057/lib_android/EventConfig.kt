package com.arch.experiments.tests.test_057.lib_android

class EventConfig<T>(
    override val onObserve: (T) -> Unit = {},
    override val onPush: (push: (T) -> Unit) -> Unit = {}
) : LinkedConfig<T>() {
    override val callObserveOnInit = false
}