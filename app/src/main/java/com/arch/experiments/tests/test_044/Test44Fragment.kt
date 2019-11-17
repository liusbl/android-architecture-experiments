package com.arch.experiments.tests.test_044

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import kotlinx.android.synthetic.main.test_043.*

// PTR: Presenter example
class Test44Fragment : BaseFragment(R.layout.test_044) {
    private val viewModel by lazy { createViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // BAD: Inconsistent data flow model

        button.setOnClickListener { viewModel.onButtonClicked() }
        editText1.onTextChanged { text -> viewModel.onText1Changed(text) }
        editText2.onTextChanged { text -> viewModel.onText2Changed(text) }

        viewModel.clickCountLiveData.observe { text ->
            clickCountTextView.text = text
        }
        viewModel.text1LiveData.observe { text ->
            editText1.setSafeText(text)
        }
        viewModel.text2LiveData.observe { text ->
            editText2.setSafeText(text)
        }
        viewModel.resultLiveData.observe { text ->
            resultTextView.text = text
        }
        viewModel.progressVisibilityLiveData.observe { isVisible ->
            progressBar.isVisible = isVisible
        }
        viewModel.toastLiveData.observe { message ->
            context?.showToast(message)
        }
    }

    private fun createViewModel() = ViewModelProviders.of(this)[Test44ViewModel::class.java]

    private fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(this@Test44Fragment, Observer(action::invoke))
    }
}