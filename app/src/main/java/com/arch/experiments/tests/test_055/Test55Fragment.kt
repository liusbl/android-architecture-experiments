package com.arch.experiments.tests.test_055

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.arch.experiments.R
import kotlinx.android.synthetic.main.test_055.*

class Test55Fragment : BaseViewModelFragment(R.layout.test_055) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = ViewModelProviders.of(this)[Test55ViewModel::class.java]

        button.setOnClickListener {
            viewModel.onClicked()
        }

        editText1.onTextChanged { text ->
            viewModel.text2LiveData.postValue(text)
            viewModel.onText2Changed()
        }

        editText2.onTextChanged { text ->
            viewModel.text2LiveData.postValue(text)
            viewModel.onText2Changed()
        }

        viewModel.text1LiveData.observe { text: String ->
            editText1.setSafeText(text)
            viewModel.onText1Changed()
        }

        viewModel.text2LiveData.observe { text: String ->
            editText2.setSafeText(text)
            viewModel.onText2Changed()
        }
    }
}