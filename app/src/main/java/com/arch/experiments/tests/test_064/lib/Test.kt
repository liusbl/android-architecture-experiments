package com.arch.experiments.tests.test_064.lib

interface Test {
    fun test(str: String, action: (String) -> Unit)
}

class Factory {
    fun create(list: List<Test>): Test {
        return object : Test {
            override fun test(str: String, action: (String) -> Unit) {
               list.fold(action, { acc: (String) -> Unit, test: Test ->
                    { str: String -> test.test(str, acc) }
                })(str)
            }
        }
    }
}

class Example {
    fun example() {
        val factory = Factory()

        val test1 = object : Test {
            override fun test(str: String, action: (String) -> Unit) {
                if (str.contains("A")) {
                    action(str)
                }
            }
        }

        val test2 = object : Test {
            override fun test(str: String, action: (String) -> Unit) {
                if (str.contains("B")) {
                    action(str)
                }
            }
        }

        val test = factory.create(listOf(test1, test2))
    }
}