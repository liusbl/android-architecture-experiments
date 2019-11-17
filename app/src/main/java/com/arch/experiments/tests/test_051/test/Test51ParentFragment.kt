package com.arch.experiments.tests.test_051.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_051.lib_android.createTextPusher
import com.arch.experiments.tests.test_051.lib_android.getFactory
import com.arch.experiments.tests.test_051.lib_core.MachineLinker
import kotlinx.android.synthetic.main.test_051_parent.*

// PTR: You should only be able to create a SINGLE factory,
//  since it wouldn't make sense to initialize the same field from multiple places.
class Test51ParentFragment : BaseFragment(R.layout.test_051_parent) {
    val editTextMachineLinker = MachineLinker(true, "G_")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test51ParentPresenter()

        showChild()

        presenter.start(
            editTextMachineLinker.createLinkedMachine(parentEditText.getFactory()),
            parentTextView.createTextPusher("initA_")
        )
    }

    private fun showChild() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test51ChildFragment.newInstance("init_val"))
            .commit()
    }
}