package com.arch.experiments.common.extensions

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun <T> T.toObservable(): Observable<T> = Observable.just(this)

fun <T> T.toSingle(): Single<T> = Single.just(this)

fun Disposable.addTo(disposables: CompositeDisposable) {
    disposables.add(this)
}