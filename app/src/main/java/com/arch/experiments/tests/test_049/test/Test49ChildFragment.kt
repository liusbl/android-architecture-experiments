package com.arch.experiments.tests.test_049.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.withArgs
import com.arch.experiments.tests.test_049.lib_android.createTextMachine
import com.arch.experiments.tests.test_049.lib_android.createTextPusherFactory
import com.arch.experiments.tests.test_049.misc.StringProvider
import kotlinx.android.synthetic.main.test_049_child.*

class Test49ChildFragment : BaseFragment(R.layout.test_049_child) {
    var childContainer: Test49ChildContainer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test49ChildPresenter(
            StringProvider(),
            (parentFragment as Test49ParentFragment).container
        )
        val container = Test49ChildContainer(
            childEditText.createTextMachine(arguments?.getString(KEY_INITIAL_TEXT)!!),
            childTextView.createTextPusherFactory()
        )
        presenter.start(container)
        childContainer = container
    }

    companion object {
        private const val KEY_INITIAL_TEXT = "initial_text"

        fun newInstance(initialText: String): Test49ChildFragment {
            return Test49ChildFragment().withArgs {
                putString(KEY_INITIAL_TEXT, initialText)
            }
        }
    }
}