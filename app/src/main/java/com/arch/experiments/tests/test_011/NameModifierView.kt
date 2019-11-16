package com.arch.experiments.tests.test_011

interface NameModifierView {
    fun setName(name: String)

    fun showError(error: Throwable)

    fun showProgress()

    fun hideProgress()
}
