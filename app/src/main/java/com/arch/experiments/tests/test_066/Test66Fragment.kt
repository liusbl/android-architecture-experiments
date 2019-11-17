package com.arch.experiments.tests.test_066

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_066.lib.AndroidMachines
import com.arch.experiments.tests.test_066.lib.ScopeHandler
import kotlinx.android.synthetic.main.test_066.*

class Test66Fragment : BaseFragment(R.layout.test_066), AndroidMachines {
    override val scopeHandler = ScopeHandler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test66Presenter()
        presenter.start(
            editText1.createTextMachine("init1_"),
            editText2.createTextMachine("init2_")
        )
    }

    override fun onDestroyView() {
        scopeHandler.clearConfigs()
        super.onDestroyView()
    }
}