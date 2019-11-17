package com.arch.experiments.tests.test_057.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_057.lib_android.AndroidMachines
import kotlinx.android.synthetic.main.test_057_child.*

class Test57ChildFragment : BaseFragment(R.layout.test_057_child), AndroidMachines {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test57ChildPresenter()
        presenter.start(
            childEditText.createTextMachine("initC_"),
            (parentFragment as Test57ParentFragment).parentMachineLinker.attachLinkedMachine()
        )
    }
}