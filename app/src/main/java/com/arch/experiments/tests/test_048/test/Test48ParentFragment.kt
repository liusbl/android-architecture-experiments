package com.arch.experiments.tests.test_048.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_048.lib_android.createClickMachine
import com.arch.experiments.tests.test_048.lib_android.createPresenterEventMachine
import com.arch.experiments.tests.test_048.lib_android.createTextMachine
import com.arch.experiments.tests.test_048.lib_android.createTextPusher
import com.arch.experiments.tests.test_048.lib_core.ContainerProvider
import kotlinx.android.synthetic.main.test_048_parent.*

class Test48ParentFragment : BaseFragment(R.layout.test_048_parent), ContainerProvider<Test48ChildContainer> {
    lateinit var container: Test48ParentContainer
    var childFragment: Test48ChildFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        container = Test48ParentContainer(
            parentButton.createClickMachine(),
            createPresenterEventMachine(Unit, {
                // TODO replace with dedicated event pusher factory method
                observe {
                    childFragment = Test48ChildFragment()
                    childFragmentManager.beginTransaction()
                        .replace(R.id.container, childFragment!!)
                        .commit()
                }
            }),
            parentEditText.createTextMachine(""),
            parentTextView.createTextPusher("")
        )
        val presenter = Test48ParentPresenter(this)
        presenter.start(container)
    }

    override fun getContainer() = childFragment?.childContainer
}