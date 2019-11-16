package com.arch.experiments.tests.test_010

interface NameModifierView {
    fun setName(name: String)

    fun showError(error: Throwable)

    fun showProgress()

    fun hideProgress()
}
