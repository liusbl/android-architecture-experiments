package com.arch.experiments.tests.test_048

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_048.test.Test48ParentFragment

class Test48Fragment : BaseFragment(R.layout.test_048) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test48ParentFragment())
            .commit()
    }
}