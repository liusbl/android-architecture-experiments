package com.arch.experiments.tests.test_059

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_059.lib.AndroidMachines
import com.arch.experiments.tests.test_059.lib.PlaceholderMachineLinker
import kotlinx.android.synthetic.main.test_059.*
import kotlinx.android.synthetic.main.test_059_list_item.view.*

class Test59Fragment : BaseFragment(R.layout.test_059) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = Test12Adapter()
        recyclerView.adapter = adapter

        setItemsButton.setOnClickListener {
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
        }
    }
}

class Test12Adapter : RecyclerView.Adapter<Test12ViewHolder>(), AndroidMachines {
    private var machineList = mutableListOf<PlaceholderMachineLinker<String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Test12ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.test_059_list_item, parent, false)
        return Test12ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return machineList.size
    }

    override fun onBindViewHolder(holder: Test12ViewHolder, position: Int) {
        holder.bind(machineList[position])
    }

    // Option 2: pass list of machines
//    fun setMachines(list: List<>) {
    // TODO set up
//    }

    // Option 1: pass list of data
    fun setList(list: List<String>) {
        // PTR: create placeholder machines here for the whole list

        for (str in list) {
            machineList.add(PlaceholderMachineLinker())
        }
        notifyDataSetChanged()
    }
}

class Test12ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), AndroidMachines {
    fun bind(editTextPlaceholder: PlaceholderMachineLinker<String>) {
        editTextPlaceholder.initMachine("", itemView.editText.getTextConfig())
    }
}