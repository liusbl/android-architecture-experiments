package com.arch.experiments.common.extensions

import io.reactivex.Observable

fun <T> T.toObservable() = Observable.just(this)