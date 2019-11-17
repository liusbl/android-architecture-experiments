package com.arch.experiments.tests.test_067.lib

import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Test

// TODO not working properly
class MachineLinkerTest {
    @Test
    fun todo1() {
        val multiLinkedMachine = MachineLinker("init")
        val uiConfig = StateConfig<String>()
        val presenterConfig = StateConfig<String>()
        val uiMachine = multiLinkedMachine.attachMachine(uiConfig)
        val presenterMachine = multiLinkedMachine.attachMachine(presenterConfig)

        val presenterResults = mutableListOf<String>()
        presenterMachine.observe { presenterResults.add(it) }

        val uiResults = mutableListOf<String>()
        uiMachine.observe { uiResults.add(it) }

        uiMachine.push("init1")
        uiMachine.push("init12")

        assertEquals(listOf("init", "init1", "init12"), presenterResults)
        assertEquals(listOf("init"), uiResults)
    }

    @Test
    fun todo2() {
        val multiLinkedMachine = MachineLinker("init")
        val uiConfig = StateConfig<String>()
        val presenterConfig = StateConfig<String>()
        val uiMachine = multiLinkedMachine.attachMachine(uiConfig)
        val presenterMachine = multiLinkedMachine.attachMachine(presenterConfig)

        val presenterResults = mutableListOf<String>()
        presenterMachine.observe { presenterResults.add(it) }

        val uiResults = mutableListOf<String>()
        uiMachine.observe { uiResults.add(it) }

        uiMachine.push("init1")
        presenterMachine.push("init12")
        uiMachine.push("init123")

        assertEquals(listOf("init", "init1", "init123"), presenterResults)
        assertEquals(listOf("init", "init12"), uiResults)
    }

    @Test
    fun todo3() {
        val multiLinkedMachine = MachineLinker("init")

        val uiConfigResults = mutableListOf<String>()
        val uiConfig = object : StateConfig<String>() {
            override fun observe(state: String) {
                uiConfigResults.add(state)
            }
        }
        val uiMachine = multiLinkedMachine.attachMachine(uiConfig)

        val presenterConfigResults = mutableListOf<String>()
        val presenterConfig = object : StateConfig<String>() {
            override fun observe(state: String) {
                presenterConfigResults.add(state)
            }
        }
        val presenterMachine = multiLinkedMachine.attachMachine(presenterConfig)

        val presenterResults = mutableListOf<String>()
        presenterMachine.observe { presenterResults.add(it) }

        val uiResults = mutableListOf<String>()
        uiMachine.observe { uiResults.add(it) }

        uiMachine.push("init1")
        presenterMachine.push("init12")
        uiMachine.push("init123")

        assertEquals("presenterResults", listOf("init", "init1", "init123"), presenterResults)
        assertEquals(
            "presenterConfigResults",
            listOf("init", "init1", "init123"),
            presenterConfigResults
        )
        assertEquals("uiResults", listOf("init", "init12"), uiResults)
        assertEquals("uiConfigResults", listOf("init", "init12"), uiConfigResults)
    }

    @Test
    fun todo4() {
        val multiLinkedMachine = MachineLinker("init")
        val uiConfig = StateConfig<String>()
        val ui2Config = StateConfig<String>()
        val presenterConfig = StateConfig<String>()

        val uiMachine = multiLinkedMachine.attachMachine(uiConfig)
        val uiMachine2 = multiLinkedMachine.attachMachine(ui2Config)
        val presenterMachine = multiLinkedMachine.attachMachine(presenterConfig)

        val presenterResults = mutableListOf<String>()
        presenterMachine.observe { presenterResults.add(it) }

        val uiResults = mutableListOf<String>()
        uiMachine.observe { uiResults.add(it) }

        val uiResults2 = mutableListOf<String>()
        uiMachine2.observe { uiResults2.add(it) }

        uiMachine.push("init1")
        presenterMachine.push("init12")
        uiMachine.push("init123")
        uiMachine2.push("init1234")
        uiMachine.push("init12345")
        presenterMachine.push("init123456")
        uiMachine2.push("init1234567")

        assertEquals(
            listOf("init", "init1", "init123", "init1234", "init12345", "init1234567"),
            presenterResults
        )
        assertEquals(listOf("init", "init12", "init1234", "init123456", "init1234567"), uiResults)
        assertEquals(
            listOf("init", "init1", "init12", "init123", "init12345", "init123456"),
            uiResults2
        )
    }

    @Test
    fun todo5() {
        val multiLinkedMachine = MachineLinker("init")

        val uiSubject = PublishSubject.create<String>()
        val uiConfigResults = mutableListOf<String>()
        val uiConfig = object : StateConfig<String>() {
            override fun observe(state: String) {
                uiConfigResults.add(state)
            }

            override fun onPush(push: (String) -> Unit) {
                uiSubject.subscribe { push(it) }
            }
        }

        val uiMachine = multiLinkedMachine.attachMachine(uiConfig)

        val presenterConfigResults = mutableListOf<String>()
        val presenterSubject = PublishSubject.create<String>()
        val presenterConfig = object : StateConfig<String>() {
            override fun observe(state: String) {
                presenterConfigResults.add(state)
            }

            override fun onPush(push: (String) -> Unit) {
                presenterSubject.subscribe { push(it) }
            }
        }
        val presenterMachine = multiLinkedMachine.attachMachine(presenterConfig)

        val presenterResults = mutableListOf<String>()
        presenterMachine.observe { presenterResults.add(it) }

        val uiResults = mutableListOf<String>()
        uiMachine.observe { uiResults.add(it) }

        uiMachine.push("init1")
        presenterSubject.onNext("init12")
        uiMachine.push("init123")
        uiSubject.onNext("init1234")
        presenterMachine.push("init12345")
        uiSubject.onNext("init123456")
        presenterSubject.onNext("init1234567")

        assertEquals(
            "presenterResults",
            listOf("init", "init1", "init123", "init1234", "init123456"),
            presenterResults
        )
        assertEquals(
            "presenterConfigResults",
            listOf("init", "init1", "init123", "init1234", "init123456"),
            presenterConfigResults
        )
        assertEquals("uiResults", listOf("init", "init12", "init12345", "init1234567"), uiResults)
        assertEquals(
            "uiConfigResults",
            listOf("init", "init12", "init12345", "init1234567"),
            uiConfigResults
        )
    }

    @Test
    fun todo6() {
        val multiLinkedMachine = MachineLinker("init")

        val uiConfigSubject = PublishSubject.create<String>()
        val uiConfig = object : StateConfig<String>() {
            override fun onPush(push: (String) -> Unit) {
                uiConfigSubject.subscribe(push)
            }
        }
        val uiMachine = multiLinkedMachine.attachMachine(uiConfig)

        val presenterResults = mutableListOf<String>()
        val presenterMachine = multiLinkedMachine.attachMachine(StateConfig())
        presenterMachine.observe { presenterResults.add(it) }

        uiConfigSubject.onNext("init1")

        val newUiConfigSubject = PublishSubject.create<String>()
        val newUiConfig = object : StateConfig<String>() {
            override fun onPush(push: (String) -> Unit) {
                newUiConfigSubject.subscribe(push)
            }
        }
        multiLinkedMachine.setConfig(uiMachine, newUiConfig)

        uiConfigSubject.onNext("init12")
        newUiConfigSubject.onNext("init123")
        uiConfigSubject.onNext("init1234")

        assertEquals(
            "presenterResults",
            listOf("init", "init1", "init123"),
            presenterResults
        )
    }

    @Test
    fun todo7() {
        val multiLinkedMachine = MachineLinker("init")

        val uiConfigSubject = PublishSubject.create<String>()
        val uiConfig = object : StateConfig<String>() {
            override fun onPush(push: (String) -> Unit) {
                uiConfigSubject.subscribe(push)
            }
        }
        val uiMachine = multiLinkedMachine.attachMachine(uiConfig)

        val presenterResults = mutableListOf<String>()
        val presenterMachine = multiLinkedMachine.attachMachine(StateConfig())
        presenterMachine.observe { presenterResults.add(it) }

        uiConfigSubject.onNext("init1")
        uiMachine.push("init12")

        val newUiConfigSubject = PublishSubject.create<String>()
        val newUiConfig = object : StateConfig<String>() {
            override fun onPush(push: (String) -> Unit) {
                newUiConfigSubject.subscribe(push)
            }
        }
        multiLinkedMachine.setConfig(uiMachine, newUiConfig)

        uiConfigSubject.onNext("init123")
        uiMachine.push("init1234")
        newUiConfigSubject.onNext("init12345")
        uiConfigSubject.onNext("init123456")

        assertEquals(
            "presenterResults",
            listOf("init", "init1", "init12", "init1234", "init12345"),
            presenterResults
        )
    }

    @Test
    fun todo8() {
        val multiLinkedMachine = MachineLinker("init")

        val uiSubject = PublishSubject.create<String>()
        val uiConfigResults = mutableListOf<String>()
        val uiConfig = object : StateConfig<String>() {
            override fun observe(state: String) {
                uiConfigResults.add(state)
            }

            override fun onPush(push: (String) -> Unit) {
                uiSubject.subscribe { push(it) }
            }
        }
        val uiMachine = multiLinkedMachine.attachMachine(uiConfig)

        val presenterSubject = PublishSubject.create<String>()
        val presenterConfigResults = mutableListOf<String>()
        val presenterConfig = object : StateConfig<String>() {
            override fun observe(state: String) {
                presenterConfigResults.add(state)
            }

            override fun onPush(push: (String) -> Unit) {
                presenterSubject.subscribe { push(it) }
            }
        }
        val presenterMachine = multiLinkedMachine.attachMachine(presenterConfig)

        val presenterResults = mutableListOf<String>()
        presenterMachine.observe { presenterResults.add(it) }

        val uiResults = mutableListOf<String>()
        uiMachine.observe { uiResults.add(it) }

        uiMachine.push("init1")
        presenterSubject.onNext("init12")

        val newConfigResults = mutableListOf<String>()
        val newUiSubject = PublishSubject.create<String>()
        val newUiConfig = object : StateConfig<String>() {
            override fun observe(state: String) {
                newConfigResults.add(state)
            }

            override fun onPush(push: (String) -> Unit) {
                newUiSubject.subscribe { push(it) }
            }
        }
        multiLinkedMachine.setConfig(uiMachine, newUiConfig)

        uiMachine.push("init123")
        uiSubject.onNext("init1234") // PTR: This should not received
        presenterMachine.push("init12345")
        newUiSubject.onNext("init123456")
        presenterSubject.onNext("init1234567")

        assertEquals(
            "presenterResults",
            listOf("init", "init1", "init123", "init123456"),
            presenterResults
        )
        assertEquals(
            "presenterConfigResults",
            listOf("init", "init1", "init123", "init123456"),
            presenterConfigResults
        )
        assertEquals("uiResults", listOf("init", "init12", "init12345", "init1234567"), uiResults)
        assertEquals("uiConfigResults", listOf("init", "init12"), uiConfigResults)
        assertEquals(
            "newConfigResults",
            listOf("init12", "init12345", "init1234567"),
            newConfigResults
        ) // TODO should changing config call observe? I guess it depends on the config
    }

    @Test
    fun `initial value is sent when observing, empty config`() {
        val machine = createEmptyConfigMachine("init")

        val observeList = createObserveList(machine)

        assertEquals("observeList", listOf("init"), observeList)
    }

    @Test
    fun `initial value is sent when observing twice, empty config`() {
        val machine = createEmptyConfigMachine("init")

        val observeList1 = createObserveList(machine)
        val observeList2 = createObserveList(machine)

        assertEquals("observeList1", listOf("init"), observeList1)
        assertEquals("observeList2", listOf("init"), observeList2)
    }

    @Test
    fun `push from the same machine doesn't notify observe, empty config`() {
        val machine = createEmptyConfigMachine("init")

        val observeList = createObserveList(machine)
        machine.push("init1")

        assertEquals("observeList", listOf("init"), observeList)
    }

    @Test
    fun `observe received latest value from push from same machine, empty config`() {
        val machine = createEmptyConfigMachine("init")

        machine.push("init1")
        val observeList = createObserveList(machine)

        assertEquals("observeList", listOf("init1"), observeList)
    }

    @Test
    fun `latest push value is used when observing, empty config`() {
        val machine = createEmptyConfigMachine("init")

        machine.push("init1")
        val observeList1 = createObserveList(machine)
        machine.push("init12")
        val observeList2 = createObserveList(machine)

        assertEquals("observeList1", listOf("init1"), observeList1)
        assertEquals("observeList2", listOf("init12"), observeList2)
    }

    @Test
    fun `two machines observe the same initial state, empty config`() {
        val (machine1, machine2) = createTwoEmptyConfigMachine("init")

        val observeList1 = createObserveList(machine1)
        val observeList2 = createObserveList(machine2)

        assertEquals("observeList1", listOf("init"), observeList1)
        assertEquals("observeList2", listOf("init"), observeList2)
    }

    @Test
    fun `single other machine get notified on pushes, empty config`() {
        val (machine1, machine2) = createTwoEmptyConfigMachine("init")

        val observeList1 = createObserveList(machine1)
        val observeList2 = createObserveList(machine2)

        machine1.push("init1")
        machine2.push("init12")
        machine1.push("init123")
        machine2.push("init1234")

        assertEquals("observeList1", listOf("init", "init12", "init1234"), observeList1)
        assertEquals("observeList2", listOf("init", "init1", "init123"), observeList2)
    }

    @Test
    fun `multiple other machines get notified on pushes, empty config`() {
        val (machine1, machine2, machine3) = createThreeEmptyConfigMachine("init")

        val observeList1 = createObserveList(machine1)
        val observeList2 = createObserveList(machine2)
        val observeList3 = createObserveList(machine3)

        machine1.push("init1")
        machine2.push("init12")
        machine3.push("init123")
        machine3.push("init1234")
        machine2.push("init12345")
        machine1.push("init123456")

        assertEquals(
            "observeList1",
            listOf("init", "init12", "init123", "init1234", "init12345"),
            observeList1
        )
        assertEquals(
            "observeList2",
            listOf("init", "init1", "init123", "init1234", "init123456"),
            observeList2
        )
        assertEquals(
            "observeList3",
            listOf("init", "init1", "init12", "init12345", "init123456"),
            observeList3
        )
    }

    @Test
    fun `initial value is sent when observing, config with observe`() {
        val (machine, configObserveList) = createObserveConfigMachineWithList("init")

        val observeList = createObserveList(machine)

        assertEquals("observeList", listOf("init"), observeList)
        assertEquals("configObserveList", listOf("init"), configObserveList)
    }

    @Test
    fun `push from the same machine doesn't notify observe, config with observe`() {
        val (machine, configObserveList) = createObserveConfigMachineWithList("init")

        val observeList = createObserveList(machine)
        machine.push("init1")

        assertEquals("observeList", listOf("init"), observeList)
        assertEquals("configObserveList", listOf("init"), configObserveList)
    }

    @Test
    fun `observe received latest value from push from same machine, config with observe`() {
        val (machine, configObserveList) = createObserveConfigMachineWithList("init")

        machine.push("init1")
        val observeList = createObserveList(machine)

        assertEquals("observeList", listOf("init1"), observeList)
//        assertEquals("configObserveList", listOf("init1"), configObserveList) // TBD: Should this be ini1?
    }

    @Test
    fun `latest push value is used when observing, config with observe`() {
        val (machine, configObserveList) = createObserveConfigMachineWithList("init")

        machine.push("init1")
        val observeList1 = createObserveList(machine)
        machine.push("init12")
        val observeList2 = createObserveList(machine)

        assertEquals("observeList1", listOf("init1"), observeList1)
        assertEquals("observeList2", listOf("init12"), observeList2)
//        assertEquals("configObserveList", listOf("init1", "init12"), configObserveList) // TBD
    }

    @Test
    fun `two machines observe the same initial state, config with observe`() {
        val linkedMachine = MachineLinker("init")
        val (machine1, configObserveList1) = attachMachineWithObserveList(linkedMachine)
        val machine2 = linkedMachine.attachMachine(StateConfig())

        val observeList1 = createObserveList(machine1)
        val observeList2 = createObserveList(machine2)

        assertEquals("observeList1", listOf("init"), observeList1)
        assertEquals("observeList2", listOf("init"), observeList2)
        assertEquals("configObserveList1", listOf("init"), configObserveList1) // TBD
    }

    @Test
    fun `single other machine get notified on pushes, config with observe`() {
        val linkedMachine = MachineLinker("init")
        val (machine1, configObserveList1) = attachMachineWithObserveList(linkedMachine)
        val machine2 = linkedMachine.attachMachine(StateConfig())

        val observeList1 = createObserveList(machine1)
        val observeList2 = createObserveList(machine2)

        machine1.push("init1")
        machine2.push("init12")
        machine1.push("init123")
        machine2.push("init1234")

        assertEquals("observeList1", listOf("init", "init12", "init1234"), observeList1)
        assertEquals("observeList2", listOf("init", "init1", "init123"), observeList2)
        assertEquals("configObserveList1", listOf("init", "init12", "init1234"), configObserveList1)
    }

    // TODO create with observe config
//    @Test
//    fun `multiple other machines get notified on pushes, config with observe`() {
//        val (machine1, machine2, machine3) = createThreeEmptyConfigMachine("init")
//
//        val observeList1 = createObserveList(machine1)
//        val observeList2 = createObserveList(machine2)
//        val observeList3 = createObserveList(machine3)
//
//        machine1.push("init1")
//        machine2.push("init12")
//        machine3.push("init123")
//        machine3.push("init1234")
//        machine2.push("init12345")
//        machine1.push("init123456")
//
//        assertEquals(
//            "observeList1",
//            listOf("init", "init12", "init123", "init1234", "init12345"),
//            observeList1
//        )
//        assertEquals(
//            "observeList2",
//            listOf("init", "init1", "init123", "init1234", "init123456"),
//            observeList2
//        )
//        assertEquals(
//            "observeList3",
//            listOf("init", "init1", "init12", "init12345", "init123456"),
//            observeList3
//        )
//    }

    @Test
    fun `initial value is sent when observing, config with push`() {
        val (machine, onPushSubject) = createPushConfigMachineWithList("init")

        val observeList = createObserveList(machine)

        assertEquals("observeList", listOf("init"), observeList)
    }

    @Test
    fun `push from the same machine doesn't notify observe, config with push`() {
        val (machine, onPushSubject) = createPushConfigMachineWithList("init")

        onPushSubject.onNext("init1")
        val observeList = createObserveList(machine)
        machine.push("init2")

        assertEquals("observeList", listOf("init1"), observeList)
    }

    @Test
    fun `latest push value is used when observing, config with push`() {
        val (machine, onPushSubject) = createPushConfigMachineWithList("init")

        machine.push("init1")
        val observeList1 = createObserveList(machine)
        onPushSubject.onNext("init12")
        machine.push("init123")
        val observeList2 = createObserveList(machine)
        onPushSubject.onNext("init1234")

        assertEquals("observeList1", listOf("init1"), observeList1)
        assertEquals("observeList2", listOf("init123"), observeList2)
    }

    @Test
    fun `single other machine get notified on pushes, config with push`() {
        val linkedMachine = MachineLinker("init")
        val (machine1, onPushSubject1) = attachMachineWithPushConfig(linkedMachine)
        val machine2 = linkedMachine.attachMachine(StateConfig())

        val observeList1 = createObserveList(machine1)
        val observeList2 = createObserveList(machine2)

        machine1.push("init1")
        machine2.push("init12")
        onPushSubject1.onNext("init123")
        machine2.push("init1234")

        assertEquals("observeList1", listOf("init", "init12", "init1234"), observeList1)
        assertEquals("observeList2", listOf("init", "init1", "init123"), observeList2)
    }

    @Test
    fun `single other machine get notified on pushes, config with push and then changed`() {
        val linkedMachine = MachineLinker("init")
        val (machine1, onPushSubject1) = attachMachineWithPushConfig(linkedMachine)
        val machine2 = linkedMachine.attachMachine(StateConfig())

        val observeList1 = createObserveList(machine1)
        val observeList2 = createObserveList(machine2)

        machine1.push("init1")
        machine2.push("init12")
        linkedMachine.setConfig(machine1, StateConfig())
        onPushSubject1.onNext("init123") // PTR: Should not be received
        machine2.push("init1234")

        assertEquals("observeList1", listOf("init", "init12", "init1234"), observeList1)
        assertEquals("observeList2", listOf("init", "init1"), observeList2)
    }

    @Test
    fun `full test`() {
        val linkedMachine = MachineLinker("init")

        val observeConfigList1 = mutableListOf<String>()
        val onPushSubject1 = PublishSubject.create<String>()
        val config1 = object : StateConfig<String>() {
            override fun observe(state: String) {
                observeConfigList1.add(state)
            }

            override fun onPush(push: (String) -> Unit) {
                onPushSubject1.subscribe(push)
            }
        }

        val machine1 = linkedMachine.attachMachine(config1)

        val observeConfigList2 = mutableListOf<String>()
        val onPushSubject2 = PublishSubject.create<String>()
        val config2 = object : StateConfig<String>() {
            override fun observe(state: String) {
                observeConfigList2.add(state)
            }

            override fun onPush(push: (String) -> Unit) {
                onPushSubject2.subscribe(push)
            }
        }
        val machine2 = linkedMachine.attachMachine(config2)

        val observeConfigList3 = mutableListOf<String>()
        val onPushSubject3 = PublishSubject.create<String>()
        val config3 = object : StateConfig<String>() {
            override fun observe(state: String) {
                observeConfigList3.add(state)
            }

            override fun onPush(push: (String) -> Unit) {
                onPushSubject3.subscribe(push)
            }
        }

        val observeList1 = mutableListOf<String>()
        machine1.observe { observeList1.add(it) }

        val observeList2 = mutableListOf<String>()
        machine2.observe { observeList2.add(it) }

        onPushSubject1.onNext("init1")
        machine1.push("init12")
        onPushSubject2.onNext("init123")
        machine2.push("init1234")

        linkedMachine.setConfig(machine1, config3)

        onPushSubject1.onNext("init12345") // PTR: Should not notify
        machine1.push("init123456")
        onPushSubject2.onNext("init1234567")
        machine2.push("init12345678")
        onPushSubject3.onNext("init123456789")

        assertEquals(
            "observeList1",
            listOf("init", "init123", "init1234", "init1234567", "init12345678"),
            observeList1
        )
        assertEquals(
            "observeList2",
            listOf("init", "init1", "init12", "init123456", "init123456789"),
            observeList2
        )
//        assertEquals(
//            "observeConfigList1",
//            listOf("init"),
//            observeConfigList1
//        )
    }

    private fun <State> createThreeEmptyConfigMachine(
        initialState: State
    ): Triple<Machine<State>, Machine<State>, Machine<State>> {
        val linkedMachine = MachineLinker(initialState)
        return Triple(
            linkedMachine.attachMachine(StateConfig()),
            linkedMachine.attachMachine(StateConfig()),
            linkedMachine.attachMachine(StateConfig())
        )
    }

    private fun <State> createTwoEmptyConfigMachine(
        initialState: State
    ): Pair<Machine<State>, Machine<State>> {
        val linkedMachine = MachineLinker(initialState)
        return linkedMachine.attachMachine(StateConfig()) to
                linkedMachine.attachMachine(StateConfig())
    }

    private fun <State> createPushConfigMachineWithList(
        initialState: State
    ): Pair<Machine<State>, PublishSubject<State>> {
        val linkedMachine = MachineLinker(initialState)
        val onPushSubject = PublishSubject.create<State>()
        val config = object : StateConfig<State>() {
            override fun onPush(push: (State) -> Unit) {
                onPushSubject.subscribe(push)
            }
        }
        return linkedMachine.attachMachine(config) to onPushSubject
    }

    private fun <State> createObserveConfigMachineWithList(
        initialState: State
    ): Pair<Machine<State>, MutableList<State>> {
        val linkedMachine = MachineLinker(initialState)
        val configObserveList = mutableListOf<State>()
        val config = object : StateConfig<State>() {
            override fun observe(state: State) {
                configObserveList.add(state)
            }
        }
        return linkedMachine.attachMachine(config) to configObserveList
    }

    private fun <State> createEmptyConfigMachine(initialState: State): Machine<State> {
        val linkedMachine = MachineLinker(initialState)
        return linkedMachine.attachMachine(StateConfig())
    }

    private fun <State> createObserveList(machine: Machine<State>): MutableList<State> {
        val observeList = mutableListOf<State>()
        machine.observe { state -> observeList.add(state) }
        return observeList
    }

    private fun <State> attachMachineWithObserveList(linkedMachine: MachineLinker<State>): Pair<Machine<State>, MutableList<State>> {
        val configObserveList = mutableListOf<State>()
        val config = object : StateConfig<State>() {
            override fun observe(state: State) {
                configObserveList.add(state)
            }
        }
        return linkedMachine.attachMachine(config) to configObserveList
    }

    private fun <State> attachMachineWithPushConfig(linkedMachine: MachineLinker<State>): Pair<Machine<State>, PublishSubject<State>> {
        val onPushSubject = PublishSubject.create<State>()
        val config = object : StateConfig<State>() {
            override fun onPush(push: (State) -> Unit) {
                onPushSubject.subscribe(push)
            }
        }
        return linkedMachine.attachMachine(config) to onPushSubject
    }
}