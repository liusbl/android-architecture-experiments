package com.arch.experiments.tests.test_038.misc

import io.reactivex.Completable
import timber.log.Timber

class MyPreferences {
    private var count = 0

    fun incrementUpdateCount() {
        Timber.d("TESTING Incrementing counter")
        count++
    }

    fun incrementUpdateCountAsync(): Completable {
        return Completable.fromAction { incrementUpdateCount() }
    }

    fun getUpdateCount(): Int {
        return count
    }
}