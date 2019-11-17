package com.arch.experiments.common.extensions

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun <T : Fragment> T.withArgs(setUpBundle: Bundle.() -> Unit): T {
    arguments = Bundle().apply(setUpBundle)
    return this
}

fun FragmentActivity.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment
) {
    supportFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .addToBackStack(fragment.javaClass.simpleName)
        .commit()
}

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.onClick(action: () -> Unit) {
    setOnClickListener { action() }
}

fun View.onClick(action: (Unit) -> Unit) {
    setOnClickListener { action(Unit) }
}