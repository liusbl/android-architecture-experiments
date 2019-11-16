package com.arch.experiments.tests.test_026

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_026.android_lib.createClickMachine
import com.arch.experiments.tests.test_026.android_lib.createMachine
import com.arch.experiments.tests.test_026.android_lib.createPresenterMachine
import com.arch.experiments.tests.test_026.android_lib.createToastMachine
import kotlinx.android.synthetic.main.test_026.*

class Test26Fragment : BaseFragment(R.layout.test_026) {
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenterStateMachine = Test26Presenter(
            countTextView.createMachine(),
            button.createClickMachine(),
            button.createAnimationMachine(),
            createToastMachine(context!!),
            editText1.createMachine(),
            editText2.createMachine()
        )

        presenterStateMachine.start() // PTR: Is this needed?
    }

    private fun View.createAnimationMachine() = createPresenterMachine(false, Unit) {
        val view = this@createAnimationMachine
        observe {
            view.animate()
                .rotationBy(360f)
                .setDuration(100)
                .start()
        }
    }
}