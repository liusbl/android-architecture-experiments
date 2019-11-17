package com.arch.experiments.tests.test_047

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_047.android_lib.createTextMachine
import com.arch.experiments.tests.test_047.android_lib.createTextPusher
import com.arch.experiments.tests.test_047.core_lib.ContainerProvider
import kotlinx.android.synthetic.main.test_047_child.*

class Test47ChildFragment : BaseFragment(R.layout.test_047_child),
    ContainerProvider<Test47ChildContainer> {
    private lateinit var container: Test47ChildContainer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test47ChildPresenter()
        container = Test47ChildContainer(
            childEditText.createTextMachine(""),
            childTextView.createTextPusher("")
        )
        presenter.start(
            container,
            (parentFragment as Test47ParentFragment).getContainer()
        )
    }

    override fun getContainer(): Test47ChildContainer = container
}