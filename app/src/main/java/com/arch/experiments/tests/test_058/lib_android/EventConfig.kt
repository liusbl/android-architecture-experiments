package com.arch.experiments.tests.test_058.lib_android

class EventConfig<T>(
    override val onObserve: (T) -> Unit = {},
    override val onPush: (push: (T) -> Unit) -> Unit = {}
) : LinkedConfig<T>() {
    override val callObserveOnInit = false
}