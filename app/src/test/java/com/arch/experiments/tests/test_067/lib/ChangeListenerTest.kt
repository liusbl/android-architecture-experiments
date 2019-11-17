package com.arch.experiments.tests.test_067.lib

import io.reactivex.subjects.PublishSubject
import org.junit.Test

class ChangeListenerTest {
    @Test
    fun changeListenerTest() {
        val changeListener = ChangeListener()
        changeListener.observe { println(it) }

        val (config, subject) = createConfigWithSubject()
        changeListener.setConfig(config)

        subject.onNext("1")

        val (newConfig, newSubject) = createConfigWithSubject()
        changeListener.setConfig(newConfig)

        subject.onNext("2--")
        newSubject.onNext("3")
    }

    private fun createConfigWithSubject(): Pair<ChangeListener.Config, PublishSubject<String>> {
        val publishSubject = PublishSubject.create<String>()
        val config = object : ChangeListener.Config {
            override fun onPush(stateConsumer: (String) -> Unit) {
                publishSubject.subscribe { stateConsumer(it) }
            }
        }
        return config to publishSubject
    }
}

class ChangeListener {
    private var observation: (String) -> Unit = {}
    private var currentListener: (String) -> Unit = {}
    private var currentConfig: Config? = null

    fun setConfig(config: Config) {
        currentConfig = config
        config.onPush { value ->
            if (currentConfig == config) {
                observation(value)
            }
        }
    }

    fun observe(onObserve: (String) -> Unit) {
        observation = onObserve
    }

    interface Config {
        fun onPush(stateConsumer: (String) -> Unit)
    }
}