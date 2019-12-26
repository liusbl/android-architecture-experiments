package com.arch.experiments.tests.test_077

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_077.lib.AndroidMachines
import com.arch.experiments.tests.test_077.lib.Config
import com.arch.experiments.tests.test_077.lib.Machine
import com.arch.experiments.tests.test_077.lib.MachineLinker
import kotlinx.android.synthetic.main.test_077.*

class Test77Fragment : BaseFragment(R.layout.test_077) {
    private val linker = LinkerContainer(
        MachineLinker("init1_"),
        MachineLinker("init2_"),
        MachineLinker("result")
    )
    private val presenter = Test77Presenter(linker) // Don't even need to do this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        linker.apply {
            editText1Linker.setConfig(editText1.getTextConfig())
            editText2Linker.setConfig(editText2.getTextConfig())
            textViewLinker.setConfig(textView.getTextConfig())
        }
        presenter.onCreate()
    }

    override fun onDestroyView() {
        linker.clear()
        super.onDestroyView()
    }

    class LinkerContainer(
        val editText1Linker: MachineLinker<String>,
        val editText2Linker: MachineLinker<String>,
        val textViewLinker: MachineLinker<String>
    ) : AndroidMachines {
        private val linkerConfigMap = mutableMapOf<MachineLinker<*>, Config<*>>()

        fun <T> MachineLinker<T>.setConfig(config: Config<T>) { // TODO should go to some base class
            // TODO Here attach to Linker and when clear is called then clear it
            linkerConfigMap[this] = config
        }

        // PTR: Shouldn't expose both observe or push
        fun <T> MachineLinker<T>.observe(onReceive: (T) -> Unit) {
            val machine = object : Machine<T> {
                override fun push(newState: T) {
                    // Empty
                }

                override fun observe(onChanged: (T) -> Unit) {
                    // Empty
                }

                override val state: T get() =
            }
            machine.observe(onReceive)

            val config = linkerConfigMap[this]
        }

        fun <T> MachineLinker<T>.push(state: T/* = current*/) {
            // Get linker attached ocnfig and push state
        }

        fun <T> MachineLinker<T>.getValue(): T {
            return
        }

        fun clear() { // TODO should go to some base class
            // TODO Clears configs that were setNOT observe and push configs, i guess

        }
    }
}