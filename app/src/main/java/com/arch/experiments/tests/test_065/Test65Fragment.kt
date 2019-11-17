package com.arch.experiments.tests.test_065

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_065.lib.AndroidMachines
import kotlinx.android.synthetic.main.test_065.*

class Test65Fragment : BaseFragment(R.layout.test_065), AndroidMachines {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test65Presenter()
        presenter.start(
            editText1.createTextMachine("init1_"),
            editText2.createTextMachine("init2_")
        )
    }
}