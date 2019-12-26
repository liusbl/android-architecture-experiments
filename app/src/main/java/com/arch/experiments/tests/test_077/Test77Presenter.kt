package com.arch.experiments.tests.test_077

// PTR: linkers shouldn't be exposed to presenter, probably??
class Test77Presenter(private val linkerContainer: Test77Fragment.LinkerContainer) {
    fun onCreate() {
        linkerContainer.apply {
            editText1Linker.observe { text1 ->
                editText2Linker.push(/*editText2Linker.value + "x"*/text1)
            }
        }
    }
}