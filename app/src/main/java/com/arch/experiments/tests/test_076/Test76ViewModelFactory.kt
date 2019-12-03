package com.arch.experiments.tests.test_076

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Test76ViewModelFactory(val value: ExampleDependency76) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = Test76ViewModel(value) as T
}