package com.arch.experiments.tests.test_028.lib

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel<State : Any, Action : Any, Effect>(
    private val initialState: State,
    private val observeScheduler: Scheduler = AndroidSchedulers.mainThread() // TODO remove this, since it adds additional dependency. Maybe move to lib/android
) {
    private val stateStream = BehaviorSubject.createDefault<State>(initialState)
    protected val state: State get() = stateStream.value ?: initialState

    private val effectStream = PublishSubject.create<Effect>()

    val unhandledErrorStream = PublishSubject.create<Throwable>()
    val THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM = PublishSubject.create<Throwable>()

    private val disposables = CompositeDisposable()

    protected abstract fun onAction(action: Action): Observable<State>

    protected fun postEffects(vararg effects: Effect) {
        effects.forEach(effectStream::onNext)
    }

    fun init(state: State) {
        stateStream.onNext(state)
    }

    fun postAction(action: Action) {
        onAction(action)
            .subscribe(stateStream::onNext, unhandledErrorStream::onNext)
            .addTo(disposables)
    }

    fun postStateUpdate(action: Action) {
        // Do same as action but don't renotify the observer
//        onAction(action)
//            .subscribe(stateStream::onNext, unhandledErrorStream::onNext)
//            .addTo(disposables)
    }


    fun reset() {
        init(initialState)
    }

    fun observeEffects(handleEffect: (Effect) -> Unit) {
        effectStream
            .observeOn(observeScheduler)
            .subscribe({ effect -> handleEffect(effect) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    fun observeState(setState: (State) -> Unit) {
        stateStream
            .observeOn(observeScheduler)
            .subscribe({ state -> setState(state) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    fun <Value> observe(getStateValue: (State) -> Value, setValue: (Value) -> Unit) {
        stateStream
            .map { state -> getStateValue.invoke(state) }
            .observeOn(observeScheduler)
            .subscribe({ value -> setValue(value) }) {
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