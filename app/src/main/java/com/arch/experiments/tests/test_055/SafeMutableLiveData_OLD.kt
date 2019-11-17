package com.arch.experiments.tests.test_055

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SafeMutableLiveData_OLD<T> : MutableLiveData<T>() {
    private var valueBuffer = mutableListOf<Any?>()

    fun <T> observe(
        owner: LifecycleOwner,
        liveData: LiveData<T>,
        onObserve: (T) -> Unit
    ) {
        liveData.observe(owner, Observer { value ->
            synchronized(valueBuffer) {
                if (!valueBuffer.contains(value)) {
                    onObserve(value)
                }
                valueBuffer.remove(value)
            }
        })
    }

    fun <T> postValue(
        liveData: MutableLiveData<T>,
        value: T
    ) {
        liveData.postValue(value)
    }

    fun <T> postValueFromView(
        liveData: MutableLiveData<T>,
        value: T
    ) {
        synchronized(valueBuffer) {
            valueBuffer.add(value)
            liveData.postValue(value)
        }
    }
}