package com.arch.experiments.tests.test_043

interface Test43View {
    fun setClickCountText(text: String)

    // PTR: Much boilerplate just for updating view state from presenter
    fun setText1(text: String)

    // PTR: Much boilerplate just for updating view state from presenter
    fun setText2(text: String)

    fun setResult(text: String)

    fun setProgressVisibility(isVisible: Boolean)

    fun showToastMessage(message: String)
}