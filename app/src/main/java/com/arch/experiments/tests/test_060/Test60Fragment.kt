package com.arch.experiments.tests.test_060

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_060.lib.AndroidMachines
import kotlinx.android.synthetic.main.test_060.*

class Test60Fragment : BaseFragment(R.layout.test_060), AndroidMachines {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test60Presenter()
        presenter.start(
            editText1.createTextMachine("init1_"),
            editText2.createTextMachine("init2_")
        )
    }
}