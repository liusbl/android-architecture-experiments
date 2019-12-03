package com.arch.experiments.tests.test_076

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import timber.log.Timber

class Test76Fragment : BaseFragment(R.layout.test_076) {
    val viewModel: Test76ViewModel by lazy {
        ViewModelProviders.of(
            this,
            Test76ViewModelFactory(ExampleDependency76())
        ).get(Test76ViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel
        Timber.e("test76 onViewCreated")
    }
}