package com.arch.experiments.tests.test_055

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

abstract class BaseViewModelFragment(
    @LayoutRes private val layoutRes: Int
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRes, container, false)

    abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)

    protected fun <T> LiveData<T>.observe(onObserve: (T) -> Unit) {
        (this as SafeMutableLiveData<T>).observe(this@BaseViewModelFragment, this, onObserve)
    }

    protected fun <T> LiveData<T>.postValue(value: T) {
        (this as SafeMutableLiveData<T>).postValue(this, value,
            CalledFrom.VIEW
        )
    }
}