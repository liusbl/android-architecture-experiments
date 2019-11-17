package com.arch.experiments.tests.test_032.misc

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class Greeter {
    fun createGreeting(name: String): Observable<String> {
        return Observable.timer(2, TimeUnit.SECONDS)
            .map { name + name }
    }
}