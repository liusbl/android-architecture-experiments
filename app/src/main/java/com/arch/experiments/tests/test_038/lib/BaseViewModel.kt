package com.arch.experiments.tests.test_038.lib

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
    private val stateStream = BehaviorSubject.createDefault<State>(initialState)
    private val effectStream = PublishSubject.create<ToggleEffect>()
    private val disposables = CompositeDisposable()

    protected val state: State get() = stateStream.value ?: initialState

    val unhandledErrorStream = PublishSubject.create<Throwable>()
    val THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM = PublishSubject.create<Throwable>()


    fun pushAction(
        setState: State.() -> ToggleAction
    ) {
        // TODO
    }

    fun <Field> observe(
        getField: State.() -> Field,
        setField: (Field) -> Unit
    ) {
        stateStream
//            .distinctUntilChanged { state -> state.getField() }
            .map { state -> state.getField() }
            .observeOn(observeScheduler)
            .subscribe({ setField(it) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    fun <Value> reactToEffect(
        getEffect: State.() -> Effect<Value>,
        react: (Value) -> Unit
    ) {
        stateStream
//            .distinctUntilChanged { state -> state.getField() }
            .map { state -> state.getEffect() }
            .observeOn(observeScheduler)
            .subscribe({ react(it.value) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    fun pushAction(
        onListenerCalled: (() -> Unit) -> Unit,
        setState: State.(ToggleAction) -> State
    ) {
        // TODO
    }

    fun <T> pushState(
        onListenerCalled: ((T) -> Unit) -> Unit,
        setState: State.(T) -> State
    ) {
        // TBD: Could track listeners and check if they are not repeated twice?
        onListenerCalled { value: T ->
            val newState = state.setState(value)
            stateStream.onNext(newState)
        }
    }

//    protected fun onAction(
//        getToggle: State.() -> ToggleAction,
//        action: () -> Unit
//    ) {
//         TODO
//    }

    protected fun onAction(
        getToggle: State.() -> ToggleAction,
        getEffect: () -> State
    ) {
        // TODO
    }

    protected fun onAction(
        getToggle: State.() -> ToggleAction,
        getEffect: Observable<() -> State>
    ) {
        // TODO
    }

    protected fun <Field> onState(
        getField: State.() -> Field,
        setState: () -> State
    ) {
        stateStream
            .distinctUntilChanged { state -> state.getField() }
            .observeOn(observeScheduler)
            .subscribe({
                //                stateStream.onNext(setState(it))
            }, {
            })
            .addTo(disposables)
    }

    protected fun <Field> onState(
        getField: State.() -> Field,
        setState: Observable<State>
//        setState: () -> Observable<State> TODO maybe like this?
    ) {
        stateStream
            .distinctUntilChanged { state -> state.getField() }
            .observeOn(observeScheduler)
            .subscribe({
                //                stateStream.onNext(setState(it))
            }, {
            })
            .addTo(disposables)
    }

    protected fun addEffect(setState: State.(ToggleEffect) -> State): State {
        TODO()
    }

    protected fun State.withEffect(setState: State.(ToggleEffect) -> State): State {
        TODO()
    }


    // TBD: Plural or singular?
    fun <T, E : Effect<T>> observeEffect(
        getEffect: (State) -> E,
        handleEffect: (T) -> Unit
    ) {
        // TODO
    }

    fun init(state: State) {
        stateStream.onNext(state)
    }

    fun reset() {
        init(initialState)
    }

    fun observeFullState(setState: (State) -> Unit) {
        stateStream
            .observeOn(observeScheduler)
            .subscribe({ state -> setState(state) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    fun clearDisposables() {
        disposables.clear()
    }

    private fun Disposable.addTo(disposables: CompositeDisposable) {
        disposables.add(this)
    }
}