package com.arch.experiments.common.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment
) {
    supportFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .addToBackStack(fragment.javaClass.simpleName)
        .commit()
}
