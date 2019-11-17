package com.arch.experiments.tests.test_045.android_lib

import androidx.lifecycle.MutableLiveData
import com.arch.experiments.tests.test_045.core_lib.Machine

// TODO Optimize for only two machines, as in:
//  Should create by default for two machines,
//  and if "addMachine" is called,
//  then adjust accordingly
class GlobalViewModelMachine<State>(
//    private val machine1: Machine<State>,
//    private val machine2: Machine<State>,
    private val callObserveOnInit: Boolean,
    private val initialState: State
) {
    private val observations =
        mutableSetOf<Observation<State>>() // TODO convert to map for faster search
    private var stateLiveData: MutableLiveData<State> = MutableLiveData<State>().apply {
//        if (callObserveOnInit) {
            postValue(initialState)
//        } else {
//            value = initialState
//        }
    }

    fun push(machine: Machine<State>, update: State) {
        stateLiveData.postValue(update)
        observations.filterNot { observation -> observation.machine === machine }
            .forEach { observation -> observation.onChanged() }
    }

    fun observe(machine: Machine<State>, onChanged: () -> Unit) {
        if (callObserveOnInit) {
            onChanged() // PTR: if we want to  not notify observers on initial state then comment out this line
        }
        observations.add(Observation(machine, onChanged))
    }

    fun createMachine() = object : Machine<State> {
        override val value: State
            get() = stateLiveData.value ?: initialState

        override fun push(update: State) {
            this@GlobalViewModelMachine.push(this, update)
        }

        override fun observe(onChanged: () -> Unit) {
            this@GlobalViewModelMachine.observe(this, onChanged)
        }
    }

    private data class Observation<State>(
        val machine: Machine<State>,
        val onChanged: () -> Unit
    )
}