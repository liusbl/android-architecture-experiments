package com.arch.experiments.tests.test_070

open class BaseState<UiEffect : BaseEffect> {
    internal var current: UiEffect? = null
    val effect get() = current.apply { current = null }
}
