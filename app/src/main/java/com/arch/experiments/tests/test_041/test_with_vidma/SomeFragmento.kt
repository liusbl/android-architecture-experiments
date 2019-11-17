package com.arch.experiments.tests.test_041.test_with_vidma

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import kotlinx.android.synthetic.main.test_041.*

class SomeFragmento : BaseFragment(R.layout.test_041) {
//    @Inject
//    lateinit var fdsfds: SomeViewModel
    // TODO solujtion: create in base class , don't inject here.
    //   When you create fragment, viewModel should be


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sudas = SomeBreroSkryno.synglTon()
            .sudas

        nameEditText.onTextChanged { text ->
            sudas.pushState { this.copy(name = text) }
        }

        sudas.observe({ name }, { nameEditText.setSafeText(name) })
    }

    companion object {
        fun createInstanso(): SomeFragmento =
            SomeFragmento()
    }
}