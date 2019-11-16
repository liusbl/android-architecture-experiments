package com.arch.experiments.tests.test_005.misc

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NameLoader {
    fun load(name: String): Single<String> {
        return Single.timer(2, TimeUnit.SECONDS, Schedulers.computation())
            .map { "Hello, $name" }
    }
}