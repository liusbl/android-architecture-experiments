package com.arch.experiments.tests.test_055

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val disposable = CompositeDisposable()

    protected fun <T> createMutableLiveData(): LiveData<T> =
        SafeMutableLiveData<T>()

    protected fun <T> LiveData<T>.postValue(value: T) {
        (this as SafeMutableLiveData<T>).postValue(this, value,
            CalledFrom.VM
        )
    }

    protected fun <T> LiveData<T>.observe(value: T) {
        (this as SafeMutableLiveData<T>).postValue(this, value,
            CalledFrom.VM
        )
    }

    open fun onCreated() {
        // Empty
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun Disposable.attachToViewModel(): Disposable = this.also { disposable.add(it) }

//    val MutableLiveData: Any = TODO()
//    val SingleLiveData: Any = TODO()
}