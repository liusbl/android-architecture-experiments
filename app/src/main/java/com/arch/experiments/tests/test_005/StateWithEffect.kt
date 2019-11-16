package com.arch.experiments.tests.test_005

interface StateWithEffect<UiEffect : Effect> {
    val effect: UiEffect
}