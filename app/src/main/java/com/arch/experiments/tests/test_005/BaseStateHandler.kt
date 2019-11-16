package com.arch.experiments.tests.test_005

import com.arch.experiments.common.extensions.addTo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

abstract class BaseStateHandler<State : StateWithEffect<UiEffect>, UserAction : Any, UiEffect : Effect> {
    protected val stateUpdates = BehaviorSubject.create<State>()
    protected val currentState: State
        get() = stateUpdates.value ?: defaultState

    private val uiEffects = PublishSubject.create<UiEffect>()
    private var lastEffect: UiEffect? = null
    private val disposables = CompositeDisposable()

    abstract val defaultState: State

    abstract fun updateState(action: UserAction, state: State): State

    abstract fun updateStateAsync(): List<Observable<State>>

    fun start() {
        this.updateStateAsync()
            .forEach { operation ->
                operation.subscribe { state ->
                    stateUpdates.onNext(state)
                    if (lastEffect != state.effect) {
                        uiEffects.onNext(state.effect)
                        lastEffect = state.effect
                    }
                }.addTo(disposables)
            }
    }

    fun dispatch(action: UserAction) {
        stateUpdates.onNext(updateState(action, currentState))
    }

    fun <Value> onStateChange(getStateValue: (State) -> Value, setValue: (Value) -> Unit) {
        stateUpdates.distinctUntilChanged { state -> getStateValue(state) }
            .map { state -> getStateValue.invoke(state) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ value -> setValue(value) }) { throwable -> Timber.e(throwable) }
            .addTo(disposables)
    }

    fun onUiEffect(handleEffect: (UiEffect) -> Unit) {
        stateUpdates.distinctUntilChanged { state -> state.effect }
            .map { state -> state.effect }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ effect -> handleEffect.invoke(effect) }, { throwable -> Timber.e(throwable) })
            .addTo(disposables)
    }

    fun <T : UserAction> onUserAction(clazz: Class<T>): Observable<T> = stateUpdates.ofType(clazz)

    fun dispose() {
        disposables.clear()
    }
}
