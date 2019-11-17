package com.arch.experiments.tests.test_051

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_051.test.Test51ParentFragment

class Test51Fragment : BaseFragment(R.layout.test_051) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test51ParentFragment())
            .commit()
    }
}