package com.arch.experiments.tests.test_027

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arch.experiments.R
import kotlinx.android.synthetic.main.test_027.*

class Test27Fragment : BaseViewModelFragment(R.layout.test_027) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = ViewModelProviders.of(this)[Test27ViewModel::class.java]
        editText.onTextChanged { viewModel.textLiveData.postValue(it) }

        button.setOnClickListener { viewModel.onButtonClicked() }

        viewModel.textLiveData.observe(this, Observer {
            editText.setSafeText(it)
        })
    }
}