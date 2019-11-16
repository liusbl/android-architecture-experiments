package com.arch.experiments.tests.test_003

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class Test3Feature : BaseFeature<Test3Feature.State, Test3Feature.Action>() {
    override fun effects(): List<Observable<Action>> {
        return listOf(effect1, effect2)
    }

    override fun reduce(state: State, action: Action): State {
        return state.copy(a = "fddf")
    }

    private val effect1 = Observable.timer(1, TimeUnit.SECONDS)
        .doOnNext { println("effect1 finished") }
        .flatMap { Observable.empty<Action>() }

    private val effect2: Observable<Action> = actions.ofType(Action.SomeUiEvent::class.java)
        .doOnNext { println("some ui event came") }
        .map { Action.SomeEffect }

    data class State(val a: String)

    sealed class Action {
        object SomeUiEvent : Action()
        object SomeEffect : Action()
    }
}