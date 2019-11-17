package com.arch.experiments.tests.test_060.lib

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