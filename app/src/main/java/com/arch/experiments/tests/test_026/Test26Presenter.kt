package com.arch.experiments.tests.test_026

import com.arch.experiments.tests.test_026.core_lib.Machine
import com.arch.experiments.tests.test_026.core_lib.Observer
import com.arch.experiments.tests.test_026.core_lib.Pusher

class Test26Presenter(
    private val textViewMachine: Pusher<String>,
    private val buttonMachine: Observer<Unit>,
    private val buttonAnimationMachine: Pusher<Unit>,
    private val toastMachine: Pusher<String>,
    private val editTextMachine1: Machine<String>,
    private val editTextMachine2: Machine<String>
) {
    private var count = 0

    fun start() {
        buttonMachine.observe {
            count++
            buttonAnimationMachine.push()
            toastMachine.push { "Updated count: $count" }
            textViewMachine.push { "Clicked $count times" }
        }
        editTextMachine1.observe {
            editTextMachine2.push {
                // TODO would be nice to have easy way to get value here
                "${editTextMachine2.value}*"
            }
        }
        editTextMachine2.observe {
            editTextMachine1.push { "${editTextMachine1.value}+" }
        }
    }
}