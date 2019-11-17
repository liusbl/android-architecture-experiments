package com.arch.experiments.tests.test_043

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_043.misc.Combiner
import com.arch.experiments.tests.test_043.misc.StringProvider
import kotlinx.android.synthetic.main.test_043.*

// PTR: Presenter example
class Test43Fragment : BaseFragment(R.layout.test_043), Test43View {
    private val presenter by lazy {
        Test43Presenter(StringProvider(), Combiner(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onViewCreated()

        button.setOnClickListener { presenter.onButtonClicked() }
        editText1.onTextChanged { text -> presenter.onText1Changed(text) }
        editText2.onTextChanged { text -> presenter.onText2Changed(text) }
    }

    // BAD: boilerplate just for setting state
    override fun setClickCountText(text: String) {
        clickCountTextView.text = text
    }

    // BAD: boilerplate just for setting state
    override fun setText1(text: String) {
        editText1.setSafeText(text)
    }

    // BAD: boilerplate just for setting state
    override fun setText2(text: String) {
        editText2.setSafeText(text)
    }

    // BAD: boilerplate just for setting state
    override fun setResult(text: String) {
        resultTextView.text = text
    }

    // BAD: boilerplate just for setting state
    override fun setProgressVisibility(isVisible: Boolean) {
        progressBar.isVisible = isVisible
    }

    override fun showToastMessage(message: String) {
        context?.showToast(message)
    }
}