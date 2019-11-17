package com.arch.experiments.tests.test_058.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_058.lib_android.AndroidMachines
import kotlinx.android.synthetic.main.test_058_child.*

class Test58ChildFragment : BaseFragment(R.layout.test_058_parent), AndroidMachines {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val parentMachineLinker =
            (parentFragment as Test58ParentFragment).parentMachineLinker
        val undefinedChildMachineLinker =
            (parentFragment as Test58ParentFragment).undefinedChildMachineLinker

        val presenter = Test58ChildPresenter()
        presenter.start(
            undefinedChildMachineLinker.initialize("initC_", childEditText.getTextConfig()),
            parentMachineLinker.attachLinkedMachine()
        )
    }
}