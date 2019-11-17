package com.arch.experiments.tests.test_047

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_047.android_lib.createClickMachine
import com.arch.experiments.tests.test_047.android_lib.createPresenterEventMachine
import com.arch.experiments.tests.test_047.android_lib.createTextMachine
import com.arch.experiments.tests.test_047.android_lib.createTextPusher
import kotlinx.android.synthetic.main.test_047_parent.*

class Test47ParentFragment : BaseFragment(R.layout.test_047_parent) {
    private val childContainerContainer = Test47ChildContainerContainer()
    private lateinit var container: Test47ParentContainer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test47ParentPresenter()
        container = Test47ParentContainer(
            button.createClickMachine(),
            createPresenterEventMachine(Unit, {
                // TODO replace with dedicated event pusher factory method
                observe {
                    val iteration5Fragment = Test47ChildFragment()
                    // BAD api.
                    childContainerContainer.childContainer1Provider = iteration5Fragment
                    childFragmentManager.beginTransaction()
                        .replace(R.id.container, iteration5Fragment)
                        .commit()
                }
            }),
            parentEditText.createTextMachine(""),
            parentTextView.createTextPusher("")
        )
        presenter.start(container, childContainerContainer)
    }

    fun getContainer() = container
}