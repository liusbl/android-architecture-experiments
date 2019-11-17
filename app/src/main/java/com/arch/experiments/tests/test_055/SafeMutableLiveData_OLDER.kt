package com.arch.experiments.tests.test_055

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

// BAD: THIS WILL FAIL WHEN WORKING WITH MULTIPLE FIELDS

// TODO try to make it so that the latest postValueFromVM value is attached to the new one (that's coming from viewModel)
class SafeMutableLiveData_OLDER {
    private var valueBuffer = mutableListOf<Any?>()

    fun <T> observe(
        owner: LifecycleOwner,
        liveData: LiveData<T>,
        onObserve: (T) -> Unit
    ) {
        liveData.observe(owner, Observer { value ->
            synchronized(valueBuffer) {
                if (valueBuffer.contains(value)) {
                    valueBuffer.remove(value)
                } else {
//                    valueBuffer.clearConfig()
                    onObserve(value)
                }
            }
        })
    }

    fun <T> postValueFromVM(
        liveData: MutableLiveData<T>,
        value: T
    ) {
        synchronized(valueBuffer) {
            valueBuffer.add(value)
//            liveData.postValueFromVM(value)
        }
    }
}