package com.arch.experiments.tests.test_041.test_with_vidma

import io.reactivex.Completable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MockPrefs {
    private var name = ""
    private var surname = ""

    fun setName(name: String) {
        this.name = name
        Timber.d("TESTING setting name: $name")
    }

    fun getName(): String {
        return name
    }

    fun setSurname(surname: String): Completable {
        return Completable.timer(200, TimeUnit.MILLISECONDS)
            .doOnComplete {
                this.surname = surname
                Timber.d("TESTING setting surname: $surname")
            }
    }
}