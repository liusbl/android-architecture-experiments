package com.arch.experiments.tests.test_067.lib

import android.annotation.SuppressLint
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Test

class ConfigTransformationTest {
    @Test
    fun test1() {
        val machineLinker = MachineLinker("init")

        val config = TestConfig()
        val machine1 = machineLinker.attachMachine(config)

        val machine2 = machineLinker.attachMachine(StateConfig())

        machine2.push("AA")
        machine2.push("BB")
        machine2.push("AB")

        assertEquals("config.observeList", listOf("init", "AA", "AB"), config.observeList)
    }

    @Test
    fun test2() {
        val machineLinker = MachineLinker("init")

        val config = TestConfig()
        val machine1 = machineLinker.attachMachine(config)

        val machine2 = machineLinker.attachMachine(StateConfig())

        val observeList2 = mutableListOf<String>()
        machine2.observe {
            observeList2.add(it)
        }

        config.subject.onNext("AA")
        config.subject.onNext("BB")
        config.subject.onNext("AB")

        assertEquals("observeList2", listOf("init", "BB", "AB"), observeList2)
    }

    @Test
    fun test3() {
        val machineLinker = MachineLinker("init")

        val transformation1 = TestTransformation()
        val transformation2 = TestTransformation2()
        val transformation = ConfigTransformationFactory<String>()
            .create(listOf(transformation1, transformation2))
        val config = TestConfig(transformation)
        val machine1 = machineLinker.attachMachine(config)

        val machine2 = machineLinker.attachMachine(StateConfig())

        machine2.push("AA")
        machine2.push("BBa")
        machine2.push("AB")
        machine2.push("AAa")
        machine2.push("BB")
        machine2.push("ABa")

        assertEquals("config.observeList", listOf("init", "AAa", "ABa"), config.observeList)
    }

    class TestTransformation : ConfigTransformation<String> {
        override fun transformObserve(state: String, observe: (String) -> Unit) {
            if (state.contains("A")) {
                observe(state)
            }
        }

        override fun transformOnPush(state: String, push: (String) -> Unit) {
            if (state.contains("B")) {
                push(state)
            }
        }
    }

    class TestTransformation2 : ConfigTransformation<String> {
        override fun transformObserve(state: String, observe: (String) -> Unit) {
            if (state.contains("a")) {
                observe(state)
            }
        }

        override fun transformOnPush(state: String, push: (String) -> Unit) {
            if (state.contains("b")) {
                push(state)
            }
        }
    }

    @SuppressLint("CheckResult")
    class TestConfig(
        override var transformation: ConfigTransformation<String> = TestTransformation()
    ) : StateConfig<String>(transformation) {
        val subject = PublishSubject.create<String>()
        val observeList = mutableListOf<String>()

        override fun observe(state: String) {
            observeList.add(state)
        }

        override fun onPush(push: (String) -> Unit) {
            subject.subscribe { push(it) }
        }
    }
}