package com.arch.experiments.tests.test_073

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.inflate
import com.arch.experiments.tests.test_073.lib.*
import kotlinx.android.synthetic.main.test_073.*
import kotlinx.android.synthetic.main.test_073_list_item.view.*

@Suppress("LocalVariableName")
class Test73Fragment : BaseFragment(R.layout.test_073), AndroidMachines {
    override val machineScope = MachineScope()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test73Presenter()

        val adapter = Adapter()
        recyclerView.adapter = adapter

        val list = (1..50).map {
            if (it % 5 == 0) {
                ViewData(it.toString(), true)
            } else {
                ViewData(it.toString(), false)
            }
        }

        adapter.setItems(list)
    }

    override fun onDestroyView() {
        machineScope.clearConfigs()
        super.onDestroyView()
    }

    class Adapter : RecyclerView.Adapter<ViewHolder>() {
        private val linkersList = mutableListOf<Linkers>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = parent.inflate(R.layout.test_073_list_item)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return linkersList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(linkersList[position])
        }

        override fun onViewRecycled(holder: ViewHolder) {
            holder.unbind(linkersList[holder.adapterPosition])
        }

        fun setItems(list: List<ViewData>) {
            list.forEach { item ->
                val linker_text = MachineLinker(item.text)
                val machine1_text = linker_text.attachMachine(StateConfig())
                val machine2_text = linker_text.attachMachine(StateConfig())
                val androidMachine = object : AndroidMachines {
                    override val machineScope: MachineScope = MachineScope()
                }
                androidMachine.machineScope.addLinkedMachines(
                    linker_text,
                    machine1_text,
                    machine2_text
                )

                val linker_switch = MachineLinker(item.isChecked)
                val machine1_switch = linker_switch.attachMachine(StateConfig())
                val machine2_switch = linker_switch.attachMachine(StateConfig())

                val linkers = object : Linkers(
                    linker_text, machine1_text, linker_switch, machine1_switch
                ) {
                    override val machineScope = MachineScope()
                }

                linkers.machineScope.addLinkedMachines(linker_text, machine1_text, machine2_text)
                linkers.machineScope.addLinkedMachines(linker_switch, machine1_switch, machine2_switch)

                linkersList.add(linkers)
            }
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(linkers: Linkers) {
            linkers.apply {
                val textConfig = itemView.editText.getTextConfig()
                val switchConfig = itemView.someSwitch.getCheckConfig()

                textLinker.setConfig(textMachine, textConfig)
                switchLinker.setConfig(switchMachine, switchConfig)
            }
        }

        fun unbind(linkers: Linkers) {
            linkers.switchLinker.clearConfig(linkers.switchMachine)
            linkers.textLinker.clearConfig(linkers.textMachine)
        }
    }

    data class ViewData(val text: String, val isChecked: Boolean)

    abstract class Linkers(
        val textLinker: MachineLinker<String>,
        val textMachine: Machine<String>,
        val switchLinker: MachineLinker<Boolean>,
        val switchMachine: Machine<Boolean>
    ) : AndroidMachines
}