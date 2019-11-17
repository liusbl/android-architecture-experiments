package com.arch.experiments.tests.test_061

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_061.lib.AndroidMachines
import com.arch.experiments.tests.test_061.lib.Machine
import com.arch.experiments.tests.test_061.lib.MachineLinker
import com.arch.experiments.tests.test_061.lib.StateConfig
import kotlinx.android.synthetic.main.test_061.*
import kotlinx.android.synthetic.main.test_061_list_item.view.*
import timber.log.Timber

class Test61Fragment : BaseFragment(R.layout.test_061) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = Test14Adapter()
        recyclerView.adapter = adapter

        var bool = true
        setItemsButton.setOnClickListener {
            if (bool) {

                adapter.setList(
                    listOf(
                        "init1_",
                        "init2_",
                        "init3_",
                        "init4_",
                        "init5_",
                        "init6_",
                        "init7_",
                        "init8_",
                        "init9_",
                        "initA_",
                        "initB_",
                        "initC_",
                        "initD_",
                        "initE_",
                        "initF_",
                        "initG_",
                        "initH_",
                        "initI_",
                        "initJ_",
                        "initK_",
                        "initL_",
                        "initM_"
                    )
                )
                bool = false
            } else {
                Timber.d("TESTING MACHINE: ${adapter.machineList.map { it.first }.joinToString { it.currentState }}")
            }
        }
    }
}

class Test14Adapter : RecyclerView.Adapter<Test14ViewHolder>(), AndroidMachines {
    var machineList = mutableListOf<Pair<MachineLinker<String>, Machine<String>>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Test14ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.test_061_list_item, parent, false)
        val viewHolder = Test14ViewHolder(itemView)
        viewHolder.adapterPosition
        return viewHolder
    }

    override fun getItemCount(): Int {
        return machineList.size
    }

    override fun onBindViewHolder(holder: Test14ViewHolder, position: Int) {
        val (a, b) = machineList[position]
        holder.bind(a, b, position)
    }

    // TODO consider how to unbind, to reduce memory footprint
    override fun onViewDetachedFromWindow(holder: Test14ViewHolder) {
//        holder.unBind(holder)machineList[holder.adapterPosition]
//        holder.unBind(holder)
    }

    fun setList(list: List<String>) {
        for (str in list) {
            val machineLinker = MachineLinker(str)
            val machine = machineLinker.attachMachine(StateConfig())
            machineList.add(machineLinker to machine)
        }
        notifyDataSetChanged()
    }
}

class Test14ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), AndroidMachines {

    fun bind(machineLinker: MachineLinker<String>, machine: Machine<String>, position: Int) {
//        val itemConfig  = ListItemConfig(AndroidConfig())
        val config = StateConfig(
            onObserve = { value ->
                // TODO: Should be a dynamic way to intercept
                if (adapterPosition == position) {
                    itemView.editText.setSafeText(value)
                }
            },
            onPush = { listener: (String) -> Unit ->
                itemView.editText.onTextChanged { text ->
                    // TODO: Should be a dynamic way to intercept
                    if (adapterPosition == position) {
                        listener(text)
                    }
                }
            }
        )
        machineLinker.setConfig(machine, config)
//        machineLinker.setConfig(machine, itemView.editText.getTextConfig())
    }
}