package com.arch.experiments.tests.test_007

class LoadButton {
    data class State(val isClicked: Boolean)
}

class InputView(/*val stateHandler: StateHandler*/) {
    fun a() {
        val input = "txt"
//        stateHandler.updateInput(input)
    }

    data class State(val text: String)
}

class StopButton {

    data class State(val isClicked: Boolean)
}

class ProgressBar {

    data class State(val isVisible: Boolean)
}