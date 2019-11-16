package com.arch.experiments.common.extensions

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun <T> T.toObservable(): Observable<T> = Observable.just(this)

fun Disposable.addTo(disposables: CompositeDisposable) {
    disposables.add(this)
}