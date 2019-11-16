package com.arch.experiments.tests.test_006

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arch.experiments.common.extensions.addTo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

// TODO implement
abstract class BaseViewModel<State : StateWithEffect<UiEffect>,
        UserAction : Any, UiEffect : Effect>(private val initialState: State) : ViewModel() {
    private val stateUpdates = MutableLiveData<State>()
    val states: LiveData<State> = stateUpdates
    private val disposables = CompositeDisposable()
    protected val state: State
        get() = stateUpdates.value ?: initialState

    abstract fun updateState(action: UserAction): Observable<State>

    abstract fun State.copyWithEmptyEffect(): State

    fun dispatch(action: UserAction) {
        updateState(action).subscribe(stateUpdates::postValue)
            .addTo(disposables)
    }

    fun clearEffect() {
        stateUpdates.postValue(state.copyWithEmptyEffect())
    }

    override fun onCleared() {
        disposables.clear()
    }
}