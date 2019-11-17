package com.arch.experiments.tests.test_051.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.telesoftas.architecture.older_experiments.comparison.mvvm.lib.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val disposable = CompositeDisposable()

    protected fun <T> createMutableLiveData(): LiveData<T> = MutableLiveData<T>()

    protected fun <T> createMutableLiveData(initial: T): LiveData<T> = MutableLiveData<T>().apply {
        postValue(initial)
    }

    protected fun <T> createSingleLiveData(): LiveData<T> = SingleLiveData<T>()

    protected fun <T> LiveData<T>.postValue(value: T) {
        when (this) {
            is MutableLiveData<T> -> postValue(value)
            else -> throw Exception("Not using createMutableLiveData() or createSingleLiveData() to create live data")
        }
    }

    protected fun <T> LiveData<T>.setValue(value: T) {
        when (this) {
            is MutableLiveData<T> -> setValue(value)
            else -> throw Exception("Not using createMutableLiveData() or createSingleLiveData() to create live data")
        }
    }

    open fun onCreated() {
        // Empty
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun Disposable.attachToViewModel(): Disposable = this.also { disposable.add(it) }
}