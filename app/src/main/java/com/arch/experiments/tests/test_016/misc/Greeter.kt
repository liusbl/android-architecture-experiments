package com.arch.experiments.tests.test_016.misc

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class Greeter {
    fun createGreeting(name: String): Single<String> {
        return Single.timer(2, TimeUnit.SECONDS)
            .map { "Hello, $name" }
    }
}