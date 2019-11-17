package com.arch.experiments.tests.test_054

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_054.lib_android.createClickObserver
import com.arch.experiments.tests.test_054.lib_android.createTextMachine
import com.arch.experiments.tests.test_054.lib_android.createToastPusher
import kotlinx.android.synthetic.main.test_054.*

class Test54Fragment : BaseFragment(R.layout.test_054) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test54Presenter()
        presenter.start(
            button.createClickObserver(),
            context!!.createToastPusher(),
            editText1.createTextMachine("init1_"),
            editText2.createTextMachine("init2_")
        )
    }
}