package com.arch.experiments.tests.test_055

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SafeMutableLiveData<T> : MutableLiveData<T>() {
    private var calledFrom = CalledFrom.VIEW

    fun <T> observe(
        owner: LifecycleOwner,
        liveData: LiveData<T>,
        onObserve: (T) -> Unit
    ) {
        liveData.observe(owner, Observer { value ->
            synchronized(this.calledFrom) {
                if (this.calledFrom == CalledFrom.VM) {
                    onObserve(value)
                }
            }
        })
    }

    fun <T> postValue(
        liveData: MutableLiveData<T>,
        value: T,
        calledFrom: CalledFrom
    ) {
        synchronized(this.calledFrom) {
            this.calledFrom = calledFrom
            liveData.postValue(value)
        }
    }
}

enum class CalledFrom {
    VIEW, VM
}
