package com.arch.experiments.tests.test_045

import com.arch.experiments.tests.test_045.core_lib.Machine
import com.arch.experiments.tests.test_045.core_lib.Observer
import com.arch.experiments.tests.test_045.core_lib.Pusher
import com.arch.experiments.tests.test_045.misc.Combiner
import com.arch.experiments.tests.test_045.misc.StringProvider

// TODO integrate with ViewModel lifecycles
class Test45Presenter(
    private val stringProvider: StringProvider,
    private val combiner: Combiner
) {
    private var clickCount = 0

    fun start(
        buttonMachine: Observer<Unit>,
        clickCountMachine: Pusher<String>,
        text1Machine: Machine<String>,
        text2Machine: Machine<String>,
        toastMachine: Pusher<String>,
        progressMachine: Pusher<Boolean>,
        resultMachine: Pusher<String>
    ) {
        buttonMachine.observe {
            clickCount++
            clickCountMachine.push(stringProvider.getClickCountText(clickCount))
            toastMachine.push(stringProvider.getStartedText())
            progressMachine.push(true)

            combiner.combine(text1Machine.value, text2Machine.value)
                .subscribe({ result ->
                    toastMachine.push(stringProvider.getFinishedText())
                    progressMachine.push(false)
                    resultMachine.push(stringProvider.getResultText(result))
                }, {})
        }
        text2Machine.observe { text1Machine.push(text1Machine.value + "*") }
    }
}