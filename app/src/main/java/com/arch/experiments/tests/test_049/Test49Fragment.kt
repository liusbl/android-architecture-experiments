package com.arch.experiments.tests.test_049

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_049.test.Test49ParentFragment

class Test49Fragment : BaseFragment(R.layout.test_049) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test49ParentFragment())
            .commit()
    }
}