package com.arch.experiments.tests.test_046

import com.arch.experiments.tests.test_046.core_lib.Machine
import com.arch.experiments.tests.test_046.core_lib.Observer
import com.arch.experiments.tests.test_046.core_lib.Pusher
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

// TODO integrate with ViewModel lifecycles
class Iteration4Presenter {
    private val list = listOf(
        Test46ViewData(false, "init1"),
        Test46ViewData(false, "init2"),
        Test46ViewData(false, "init3"),
        Test46ViewData(false, "init4"),
        Test46ViewData(false, "init10"),
        Test46ViewData(false, "init20"),
        Test46ViewData(false, "init30"),
        Test46ViewData(false, "init40"),
        Test46ViewData(false, "init01"),
        Test46ViewData(false, "init02"),
        Test46ViewData(false, "init03"),
        Test46ViewData(false, "init04"),
        Test46ViewData(false, "init010"),
        Test46ViewData(false, "init020"),
        Test46ViewData(false, "init030"),
        Test46ViewData(false, "init040")
    )

    // TODO need to somehow handle the creation of new state machines coming from UI
    fun start(
        getOnMachineCreated: ((MachineContainer) -> Unit) -> Unit,
        creator: (List<Test46ViewData>) -> Unit
    ) {
        val onMachineCreated: (MachineContainer) -> Unit = { container: MachineContainer ->
            container.apply {
                buttonMachine.observe {
                    progressMachine.push(true)
                    updateText(textMachine.value)
                        .subscribe { text ->
                            progressMachine.push(false)
                            textMachine.push(text)
                        }
                }
            }
        }
        getOnMachineCreated(onMachineCreated)

        creator(list)

//        list.map { viewData: Iteration4ViewData ->
//            creator(viewData)
//        }

//        val machineList = mutableListOf<MachineContainer>()
//        machineList.forEach { machine ->
//            machine.apply {
//                buttonMachine.observe {
//                    progressMachine.push(true)
//                    updateText(textMachine.value)
//                        .subscribe { text ->
//                            progressMachine.push(false)
//                            textMachine.push(text)
//                        }
//                }
//            }
    }

    // PTR: I wan't to be able to do this for each list item:
//        buttonMachine.observe {
//            progressMachine.push(true)
//            updateText(textMachine.value)
//                .subscribe { text ->
//                    progressMachine.push(false)
//                    textMachine.push(text)
//                }
//        }

    private fun updateText(text: String): Single<String> {
        return Single.timer(2, TimeUnit.SECONDS)
            .map { text + text }
            .observeOn(AndroidSchedulers.mainThread())
    }
}

class MachineContainer(
    val buttonMachine: Observer<Unit>,
    val progressMachine: Pusher<Boolean>,
    val textMachine: Machine<String>
) {
    companion object {
        fun create() {

        }
    }
}