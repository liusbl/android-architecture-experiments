package com.arch.experiments.tests.test_048.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_048.lib_android.createTextMachine
import com.arch.experiments.tests.test_048.lib_android.createTextPusher
import kotlinx.android.synthetic.main.test_048_child.*

class Test48ChildFragment : BaseFragment(R.layout.test_048_child) {
    var childContainer: Test48ChildContainer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test48ChildPresenter((parentFragment as Test48ParentFragment).container)
        val container = Test48ChildContainer(
            childEditText.createTextMachine(""),
            childTextView.createTextPusher("")
        )
        presenter.start(container)
        childContainer = container
    }
}