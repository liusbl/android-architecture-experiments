package com.arch.experiments.tests.test_017

import com.arch.experiments.tests.test_017.Test17StateMachine.State

class Test17StateMachine(
    reactToUpdate: ((StateMachine<SomeViewData>) -> Unit) -> Unit
) : BaseStateMachine<State>(State("", "")) {
    init {
        val list = listOf(
            SomeViewData("1", "Company11", ""),
            SomeViewData("2", "Company22", ""),
            SomeViewData("3", "Company33", ""),
            SomeViewData("4", "Company44", ""),
            SomeViewData("5", "Company55", ""),
            SomeViewData("6", "Company66", ""),
            SomeViewData("7", "Company77", ""),
            SomeViewData("8", "Company88", ""),
            SomeViewData("9", "Company99", "")
        )

        val stateMachines: List<BaseStateMachine<SomeViewData>> = list.map { BaseStateMachine(it) }

        reactToUpdateRequest(
            { text1 },
            { setState -> setState(copy(text2 = "${text2}+")) }
        )

        reactToUpdateRequest(
            { text2 },
            { setState -> setState(copy(text1 = "${text1}-")) }
        )

        reactToUpdate { stateMachine: StateMachine<SomeViewData> ->
            stateMachine.react(
                { companyName },
                { copy(companyGreeting = "Hello $companyName") }
            )
        }
    }

    override fun react(
        reactionTrigger: State.() -> Any,
        stateSupplier: State.((setState: State) -> Unit) -> Unit
    ) {
        // TODO
    }

    data class State(
        val text1: String,
        val text2: String
    )
}