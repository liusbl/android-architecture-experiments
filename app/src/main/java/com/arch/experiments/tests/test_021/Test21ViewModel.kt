package com.arch.experiments.tests.test_021

import com.arch.experiments.common.extensions.set
import com.arch.experiments.common.extensions.toObservable
import com.arch.experiments.tests.test_021.Test21ViewModel.Action
import com.arch.experiments.tests.test_021.Test21ViewModel.State
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer

class Test21ViewModel : BaseViewModel<State, Action, Any>(
    State(
        listOf(
            ServerItem("first", false, false),
            ServerItem("second", false, true),
            ServerItem("3rd", false, false),
            ServerItem("fourth", false, false),
            ServerItem("ifth", false, false),
            ServerItem("sixth", false, false),
            ServerItem("seventh", false, false),
            ServerItem("first", false, false),
            ServerItem("second22", false, false),
            ServerItem("3rd22", false, false),
            ServerItem("fourth22", false, false),
            ServerItem("ifth22", false, false),
            ServerItem("sixth22", false, false),
            ServerItem("seventh22", false, false)
        )
    )
) {
    override fun onAction(action: Action): Observable<State> {
        return when (action) {
            is Action.ShouldConnectChecked -> {
                state.apply {
                    copy(
                        list = list.set(
                            action.item,
                            action.item.copy(shouldConnected = action.isChecked)
                        )
                    )
                }.toObservable()
            }
        }
    }

    data class State(val list: List<ServerItem>) : ObservableSource<State> {
        override fun subscribe(observer: Observer<in State>) {
            // Empty
        }
    }

    sealed class Action {
        data class ShouldConnectChecked(val isChecked: Boolean, val item: ServerItem) : Action()
    }
}