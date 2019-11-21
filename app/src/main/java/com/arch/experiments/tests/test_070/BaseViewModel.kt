package com.arch.experiments.tests.test_070

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arch.experiments.common.extensions.addTo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<State : BaseState<UiEffect>,
        UserAction : Any, UiEffect : BaseEffect>(
    private val initialState: State,
    private val copyState: State.() -> State // or KFunction<State> for method reference
) : ViewModel() {
    private val stateUpdates = MutableLiveData<State>()
    val states: LiveData<State> = stateUpdates
    private val disposables = CompositeDisposable()
    protected val state: State
        get() = stateUpdates.value ?: initialState

    abstract fun updateState(action: UserAction): Observable<State>

    fun dispatch(action: UserAction) {
        updateState(action).subscribe(stateUpdates::postValue)
            .addTo(disposables)
    }

    fun State.withEffect(
        newEffect: UiEffect
    ): State = copyState().apply { current = newEffect }

//    fun State.withEffects(
//        newEffect: UiEffect?
//    ): Observable<State> = copyState().apply { current = newEffect }

    override fun onCleared() {
        disposables.clear()
    }
}