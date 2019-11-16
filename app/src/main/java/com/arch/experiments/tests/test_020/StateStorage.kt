package com.arch.experiments.tests.test_020

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.WeakReference

// TODO maybe this should be a singleton, aka an `object`?
class StateStorage {
    private val stateMap = mutableMapOf<String, Any>()

    fun <State : Any, Action : Any, Effect : Any, ViewModel : BaseViewModel<State, Action, Effect>> attach(
        viewModel: ViewModel,
        activity: FragmentActivity
    ) {
        val lifecycle = activity.lifecycle
        val key = activity::class.java.canonicalName ?: activity::class.java.simpleName

        val activitySupplier = WeakReference<Activity>(activity)

        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                stateMap[key]?.let {
                    viewModel.init(it as State)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                stateMap[key] = viewModel.state
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                if (activitySupplier.get()?.isChangingConfigurations == false) {
                    // TODO How to handle Don't keep activities?
                    //  Do you need to do that? androidx viewmodel doesn't handle that
                    // I guess that depends on the use cases
                    stateMap.remove(key)
                }
                viewModel.clearDisposables()
                activitySupplier.clear()
                lifecycle.removeObserver(this)
            }
        })
    }
}