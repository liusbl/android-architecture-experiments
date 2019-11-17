package com.arch.experiments.tests.test_043.misc

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class Combiner {
    fun combine(vararg text: String): Single<String> {
        return Single.timer(2, TimeUnit.SECONDS).map {
            text.joinToString(separator = "")
        }.observeOn(AndroidSchedulers.mainThread())
    }
}