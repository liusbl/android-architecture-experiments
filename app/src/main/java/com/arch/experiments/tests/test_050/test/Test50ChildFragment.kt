package com.arch.experiments.tests.test_050.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.withArgs
import com.arch.experiments.tests.test_050.lib_android.createTextMachine
import com.arch.experiments.tests.test_050.lib_android.createTextPusherFactory
import com.arch.experiments.tests.test_050.misc.StringProvider
import kotlinx.android.synthetic.main.test_050_child.*

class Test50ChildFragment : BaseFragment(R.layout.test_050_child) {
    var childContainer: Test50ChildContainer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val containerr = (parentFragment as Test50ParentFragment).container

//        val parentTextViewLinker: MachineLinker<String> = MachineLinker(false, "")
//
//        val parentTextView = parentTextViewLinker.attachLinkedMachine()

        val presenter = Test50ChildPresenter(
            StringProvider(),
            TODO(),
            TODO()

//            (parentFragment as Test3ParentFragment).container
        )
        val container = Test50ChildContainer(
            childEditText.createTextMachine(arguments?.getString(KEY_INITIAL_TEXT)!!),
            childTextView.createTextPusherFactory()
        )
        presenter.start(container)
        childContainer = container
    }

    companion object {
        private const val KEY_INITIAL_TEXT = "initial_text"

        fun newInstance(initialText: String): Test50ChildFragment {
            return Test50ChildFragment().withArgs {
                putString(KEY_INITIAL_TEXT, initialText)
            }
        }
    }
}