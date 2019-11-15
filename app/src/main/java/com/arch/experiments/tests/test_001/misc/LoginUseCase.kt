package com.arch.experiments.tests.test_001.misc

import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class LoginUseCase {
    fun login(username: String, password: String): Completable {
        return Completable.timer(2, TimeUnit.SECONDS)
            .andThen(Completable.fromAction {
                if (password != "pass") {
                    throw Exception("Login failed")
                }
            })
    }
}