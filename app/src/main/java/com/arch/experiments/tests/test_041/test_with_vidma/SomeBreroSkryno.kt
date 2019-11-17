package com.arch.experiments.tests.test_041.test_with_vidma

class SomeBreroSkryno {
    val sudas: Sudas<SomeViewState> by lazy {
//        val initialImpl = SudasImpl(
//            SomeViewState("")
//        )
//        SomeViewModel(MockPrefs(), initialImpl)
//        initialImpl
        null!!
    }

    fun createBrero(): SomeFragmento {
        return SomeFragmento.createInstanso()
    }

    companion object {
        private val INSTANCE by lazy { SomeBreroSkryno() }

        fun synglTon(): SomeBreroSkryno =
            INSTANCE
    }
}