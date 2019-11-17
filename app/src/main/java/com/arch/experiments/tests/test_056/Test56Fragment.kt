package com.arch.experiments.tests.test_056

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_056.lib_android.createClickObserver
import com.arch.experiments.tests.test_056.lib_android.createEventPusher
import com.arch.experiments.tests.test_056.lib_android.createTextMachine
import kotlinx.android.synthetic.main.test_056.*

class Test56Fragment : BaseFragment(R.layout.test_056) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test56Presenter()
        presenter.start(
            button.createClickObserver(),
            ::showToast.createEventPusher(""),
            editText1.createTextMachine("init1_"),
            editText2.createTextMachine("init2_")
        )
    }

    fun showToast(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
    }
}