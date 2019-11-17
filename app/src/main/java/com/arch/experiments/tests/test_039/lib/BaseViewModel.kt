package com.arch.experiments.tests.test_039.lib

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel<State>(
    private val initialState: State,
    private val observeScheduler: Scheduler = AndroidSchedulers.mainThread()
    // TODO remove AndroidScheduler, since it adds additional dependency. Maybe move to lib/android
) {
    private val stateStream =
        BehaviorSubject.createDefault<StateUpdate<State>>(StateUpdate(initialState, false))

    private val effectStream = PublishSubject.create<ToggleEffect>()
    private val disposables = CompositeDisposable()

    protected val state: State get() = stateStream.value?.state ?: initialState

    val unhandledErrorStream = PublishSubject.create<Throwable>()
    val THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM = PublishSubject.create<Throwable>()

    // TBD: Do you need this? Might be useful for restoring state from cache
    fun init(state: State) {
        stateStream.onNext(StateUpdate(state, false))
    }

    fun <T> pushState(
        onListenerCalled: ((T) -> Unit) -> Unit,
        setState: State.(T) -> State
    ) {
        // TBD: Could track listeners and check if they are not repeated twice?
        onListenerCalled { value: T ->
            val newState = state.setState(value)
            stateStream.onNext(StateUpdate(newState, true))
        }
    }

    fun pushAction(
        onListenerCalled: (() -> Unit) -> Unit,
        updateState: State.(ToggleAction) -> State
    ) {
        // TODO should be synchronized somehow
        onListenerCalled {
            val activeState = state.updateState(ToggleAction.Active)
            stateStream.onNext(StateUpdate(activeState, true))

            val inactiveState = state.updateState(ToggleAction.Inactive)
            stateStream.onNext(StateUpdate(inactiveState, true))
        }
    }

    fun <Field> observe(
        getField: State.() -> Field,
        setField: (Field) -> Unit
    ) {
        stateStream
            .distinctUntilChanged { stateUpdate -> stateUpdate.state.getField() }
            .filter { stateUpdate -> !stateUpdate.isFromView }
            .map { stateUpdate -> stateUpdate.state.getField() }
            .observeOn(observeScheduler)
            .subscribe({ field -> setField(field) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addToDisposables()
    }

    fun observeEffect(
        getEffect: State.() -> ToggleEffect,
        handleEffect: () -> Unit
    ) {
        stateStream
            .distinctUntilChanged { stateUpdate -> stateUpdate.state.getEffect() }
            .filter { stateUpdate -> !stateUpdate.isFromView }
            .filter { stateUpdate -> stateUpdate.state.getEffect() is ToggleEffect.Active }
            .observeOn(observeScheduler)
            .subscribe({ handleEffect() }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addToDisposables()
    }

    fun observeFullState(setState: (State) -> Unit) {
        stateStream
            .observeOn(observeScheduler)
            .subscribe({ stateUpdate -> setState(stateUpdate.state) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addToDisposables()
    }

    protected fun <Field> onState(
        getField: State.() -> Field,
        setState: () -> State
    ) {
        stateStream
            .distinctUntilChanged { stateUpdate -> stateUpdate.state.getField() }
            .filter { stateUpdate -> stateUpdate.isFromView }
            .observeOn(observeScheduler)
            .subscribe({
                stateStream.onNext(StateUpdate(setState(), false))
            }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addToDisposables()
    }

    protected fun <Field> onState(
        getField: State.() -> Field,
        setState: Observable<() -> State>
    ) {
        stateStream
            .distinctUntilChanged { stateUpdate -> stateUpdate.state.getField() }
            .filter { stateUpdate -> stateUpdate.isFromView }
            .flatMap { setState }
            .observeOn(observeScheduler)
            .subscribe({ getState: () -> State ->
                stateStream.onNext(StateUpdate(getState(), false))
            }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addToDisposables()
    }

    protected fun onAction(
        getToggle: State.() -> ToggleAction,
        getEffect: () -> State
    ) {
        stateStream
            .distinctUntilChanged { stateUpdate -> stateUpdate.state.getToggle() }
            .filter { stateUpdate -> stateUpdate.state.getToggle() is ToggleAction.Active }
            .filter { stateUpdate -> stateUpdate.isFromView }
            .observeOn(observeScheduler)
            .subscribe({
                stateStream.onNext(StateUpdate(getEffect(), false))
            }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addToDisposables()
    }

    protected fun onAction(
        getToggle: State.() -> ToggleAction,
        getEffect: Observable<() -> State>
    ) {
        stateStream
            .distinctUntilChanged { stateUpdate -> stateUpdate.state.getToggle() }
            .filter { stateUpdate -> stateUpdate.state.getToggle() is ToggleAction.Active }
            .filter { stateUpdate -> stateUpdate.isFromView }
            .flatMap { getEffect }
            .observeOn(observeScheduler)
            .subscribe({ getState ->
                stateStream.onNext(StateUpdate(getState(), false))
            }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addToDisposables()
    }

    protected fun State.withEffect(setState: State.(ToggleEffect) -> State): Observable<() -> State> =
        Observable.just({ setState(ToggleEffect.Active) }, { setState(ToggleEffect.Inactive) })

    fun reset() {
        init(initialState)
    }

    fun clearDisposables() {
        disposables.clear()
    }

    private fun Disposable.addToDisposables() {
        disposables.add(this)
    }

    private data class StateUpdate<State>(val state: State, val isFromView: Boolean)
}