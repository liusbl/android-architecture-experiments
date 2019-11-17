package com.arch.experiments.tests.test_045

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_045.android_lib.createPresenterMachine
import com.arch.experiments.tests.test_045.misc.Combiner
import com.arch.experiments.tests.test_045.misc.StringProvider


class Test45Fragment : BaseFragment(R.layout.test_045) {
    private val combiner =
        Combiner() // BAD
    private val presenter by lazy {
        Test45Presenter(StringProvider(), combiner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        presenter.start(
//            button.createClickMachine(),
//            clickCountTextView.createMachine(), // BAD VERY, no way to get internal clickCount, so can't set initial state
//            editText1.createMachine("init1"), // BAD state initialized is all around the place
//            editText2.createMachine("init2"), // BAD state initialized is all around the place
//            createToastMachine(context!!),
//            progressBar.createVisibilityMachine(),
//            resultTextView.createMachine("No Result") // BAD state initialized is all around the place
//        )
    }

    // PTR: just a demonstration of custom machine
    private fun View.createVisibilityMachine() = createPresenterMachine(
        callObserveOnInit = true,
        initialState = false,
        setUpViewMachine = {
            observe { this@createVisibilityMachine.isVisible = value }
        })
}