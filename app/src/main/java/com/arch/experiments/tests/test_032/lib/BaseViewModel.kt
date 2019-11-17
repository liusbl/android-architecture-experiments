package com.arch.experiments.tests.test_032.lib

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel<State>(
    private val initialState: State,
    private val observeScheduler: Scheduler = AndroidSchedulers.mainThread() // TODO remove this, since it adds additional dependency. Maybe move to lib/android
) {
    private val stateStream = BehaviorSubject.createDefault<State>(initialState)
    protected val state: State get() = stateStream.value ?: initialState

    val unhandledErrorStream = PublishSubject.create<Throwable>()
    val THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM = PublishSubject.create<Throwable>()

    private val disposables = CompositeDisposable()

    fun push(
        setState: State.() -> State
    ) {
        // TODO
    }

    fun <Value> push(
        onValueUpdated: ((Value) -> Unit) -> Unit,
        setState: State.(Value) -> State
    ) {
        // Could track listeners and
        // TODO
    }

    fun <T, E : Effect<T>> reactEffect(getEffect: (State) -> E, handleEffect: (T) -> Unit) {
        // TODO
    }

    fun <Value> react(getStateValue: (State) -> Value, setValue: (Value) -> Unit) {
        stateStream
            .map { state -> getStateValue.invoke(state) }
            .observeOn(observeScheduler)
            .subscribe({ value -> setValue(value) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    // PTR: When implementing, don't forget to not renotify the listener
    fun <Value> pushAndReact(
        push: ((Value) -> Unit) -> Unit,
        setState: State.(Value) -> State,
        getValue: (State) -> Value,
        react: (Value) -> Unit // Maybe reaction could wrap somehow so that listener is not notified or something?
    ) {
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