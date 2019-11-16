package com.arch.experiments.tests.test_013.misc

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class Api {
    fun update(text: String): Single<String> {
        return Single.timer(2, TimeUnit.SECONDS)
            .flatMap {
                if (text == "fail") {
                    Single.error(Throwable("failed"))
                } else {
                    Single.just(text + text)
                }
            }
    }
}