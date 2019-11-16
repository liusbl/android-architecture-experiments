package com.arch.experiments.tests.test_007

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class Api {
    fun modifyText(text: String): Single<String> {
        return Single.timer(2, TimeUnit.SECONDS, Schedulers.computation())
                .map { text + text }
    }
}
