package com.arch.experiments.tests.test_051.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.withArgs
import com.arch.experiments.tests.test_051.lib_android.createTextMachineFactory
import com.arch.experiments.tests.test_051.lib_android.createTextPusher
import kotlinx.android.synthetic.main.test_051_child.*

class Test51ChildFragment : BaseFragment(R.layout.test_051_child) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test51ChildPresenter()

        // BAD, this should be prevented somehow! Also check why it doesn't work
//        val parentEditTextMachine: Machine<String> =
//            (parentFragment as Test4ParentFragment).editTextMachine

        presenter.start(
            (parentFragment as Test51ParentFragment).editTextMachineLinker.attachLinkedMachine(),
            childEditText.createTextMachineFactory(),
            childTextView.createTextPusher(arguments!!.getString(KEY_INITIAL_TEXT)!!)
        )
    }

    companion object {
        private const val KEY_INITIAL_TEXT = "initial_text"

        fun newInstance(initialText: String): Test51ChildFragment {
            return Test51ChildFragment().withArgs {
                putString(KEY_INITIAL_TEXT, initialText)
            }
        }
    }
}