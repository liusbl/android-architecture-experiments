package com.arch.experiments.tests.test_057.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_057.lib_android.AndroidMachines
import com.arch.experiments.tests.test_057.lib_core.CustomMachineLinker
import kotlinx.android.synthetic.main.test_057_parent.*

class Test57ParentFragment : BaseFragment(R.layout.test_057_parent), AndroidMachines {
    val parentMachineLinker by lazy {
        CustomMachineLinker("initP_", parentEditText.getTextConfig())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test57ParentPresenter()
        presenter.start(
            showChildButton.createClickObserver(),
            ::showChild.createEventPusher(),
            parentMachineLinker.attachLinkedMachine()
        )
    }

    private fun showChild() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test57ChildFragment())
            .commit()
    }
}