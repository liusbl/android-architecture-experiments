package com.arch.experiments.tests.test_006

interface StateWithEffect<UiEffect : Effect> {
    val effect: UiEffect
}