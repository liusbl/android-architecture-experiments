package com.arch.experiments.tests.test_072

import com.arch.experiments.tests.test_072.lib.Machine
import com.arch.experiments.tests.test_072.lib.push
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class Test72Presenter {
    fun start(
        textMachine1: Machine<String>,
        textMachine2: Machine<String>
    ) {
        textMachine1.observe {
            Observable.interval(5, TimeUnit.SECONDS)
                .map { textMachine2.state + "X" }
                .push(textMachine2)
        }
//        textMachine2.observe
//        {
//            textMachine1.push(textMachine1.state + "0")
//        }
    }
}