package com.arch.experiments.tests.test_058.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_058.lib_android.AndroidMachines
import com.arch.experiments.tests.test_058.lib_core.CustomMachineLinker
import com.arch.experiments.tests.test_058.lib_core.UndefinedMachineLinker
import kotlinx.android.synthetic.main.test_058_parent.*

class Test58ParentFragment : BaseFragment(R.layout.test_058_parent), AndroidMachines {
    val parentMachineLinker by lazy {
        CustomMachineLinker("initP_", parentEditText.getTextConfig())
    }

    val undefinedChildMachineLinker = UndefinedMachineLinker<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test58ParentPresenter()
        presenter.start(
            showChildButton.createClickObserver(),
            ::showChild.createEventPusher(),
            parentMachineLinker.attachLinkedMachine(),
            undefinedChildMachineLinker.attachLinkedMachine()
        )
    }

    private fun showChild() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test58ChildFragment())
            .commit()
    }
}