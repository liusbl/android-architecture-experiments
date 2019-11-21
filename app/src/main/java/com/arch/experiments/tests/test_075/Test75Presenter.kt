package com.arch.experiments.tests.test_075

import com.arch.experiments.tests.test_075.lib.Machine

class Test75Presenter {
    fun start(
        textMachineList: List<Machine<Test75ViewData>>,
        textMachine1: Machine<String>,
        textMachine2: Machine<String>
    ) {
        textMachineList.forEachIndexed { index, machine ->
            machine.observe { data ->
                textMachineList[index + 1].push(data.copy(text = data.text + "Y"))
            }
        }

        textMachine1.observe { textMachine2.push(textMachine2.state + "X") }
        textMachine2.observe { textMachine1.push(textMachine1.state + "0") }
    }
}