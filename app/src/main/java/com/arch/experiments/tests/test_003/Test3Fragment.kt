package com.arch.experiments.tests.test_003

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.addTo
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.test_003.*

class Test3Fragment : BaseFragment(R.layout.test_003) {
    private val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val feature = Test3Feature()
        feature.start(Test3Feature.State("fdjslkfdj"))

        feature.news
            .subscribe {
                println(it)
            }.addTo(disposables)

        button.setOnClickListener {
            feature.dispatch(Test3Feature.Action.SomeUiEvent)
        }
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}