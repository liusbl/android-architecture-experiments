package com.arch.experiments.tests.test_067.lib

import org.junit.Test

class TransformationListTest {
    private val factory = Factory()

    @Test
    fun test1() {
        val list = listOf(Thing1(), Thing2(), Thing3())
        val thing = factory.create(list)

        thing.observe("AaBb", {
            println(it)
        })
        thing.observe("AaBb111", {
            println(it)
        })
    }

    class Thing1 : Thing {
        override fun observe(input: String, action: (String) -> Unit) {
            if (input.contains("A")) {
                action(input)
            }
        }
    }

    class Thing2 : Thing {
        override fun observe(input: String, action: (String) -> Unit) {
            if (input.contains("a")) {
                action(input + "X")
            }
        }
    }

    class Thing3 : Thing {
        override fun observe(input: String, action: (String) -> Unit) {
            if (input.contains("1")) {
                action(input)
            }
        }
    }

    class Factory {
        fun create(list: List<Thing>): Thing {
            return object : Thing {
                override fun observe(input: String, action: (String) -> Unit) {
                    val act = list.fold(
                        action,
                        { acc: (String) -> Unit, thing: Thing ->
                            { str -> thing.observe(str, acc) }
                        }
                    )
                    act(input)
                }
            }
        }

        fun create_HACK(list: List<Thing>): Thing {
            return object : Thing {
                override fun observe(input: String, action: (String) -> Unit) {
                    list[0].observe(input, { input2 ->
                        list[1].observe(input2, { input3 ->
                            list[2].observe(input3, action)
                        })
                    })
                }
            }
        }
    }

    interface Thing {
        fun observe(input: String, action: (String) -> Unit)
    }
}