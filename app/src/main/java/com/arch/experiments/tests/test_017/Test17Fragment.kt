package com.arch.experiments.tests.test_017

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.lists.SingleViewTypeAdapter
import kotlinx.android.synthetic.main.test_017.*
import kotlinx.android.synthetic.main.test_017_list_item.view.*

class Test17Fragment : BaseFragment(R.layout.test_017) {
    private lateinit var stateMachine: Test17StateMachine

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stateMachine = Test17StateMachine {
            //TODO
        }
        setUpEditTexts()
        setUpList()
    }

    private fun setUpEditTexts() {
        editText1.onTextChanged { text -> stateMachine.push { copy(text1 = text) } }
        stateMachine.reactToUpdateRequest({ text1 }, { editText1.setSafeText(text1) })

        editText2.onTextChanged { text -> stateMachine.push { copy(text2 = text) } }
        stateMachine.reactToUpdateRequest({ text2 }, { editText2.setSafeText(text2) })
    }

    private fun setUpList() {
        val adapter = SingleViewTypeAdapter(
//            stateMachine, TBD: How to integrate
            R.layout.test_017_list_item
        ) { itemView: View, stateMachine: StateMachine<SomeViewData> ->
            itemView.companyEditText.onTextChanged { text ->
                stateMachine.push { copy(companyName = text) }
            }
        }
        recyclerView.adapter = adapter
    }
}