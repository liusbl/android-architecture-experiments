package com.arch.experiments.tests.test_027

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arch.experiments.common.BaseFragment

abstract class BaseViewModelFragment(layoutRes: Int) : BaseFragment(layoutRes) {
    protected fun <T> LiveData<T>.postValue(value: T) {
        (this as? MutableLiveData<T>)?.apply {
            // TODO find a way to safely set the value.
            //  this.value = value doesn't work
        } ?: throw Exception("\"Not using createMutableLiveData() or createSingleLiveData() to create live data\"")
    }
}