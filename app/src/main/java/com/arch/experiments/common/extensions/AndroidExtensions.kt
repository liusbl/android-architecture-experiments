package com.arch.experiments.common.extensions

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
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

fun EditText.doAfterTextChanged(onTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(value: Editable?) {
            value?.toString()?.let(onTextChanged::invoke)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Empty
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Empty
        }
    })
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    val inflater = LayoutInflater.from(context)
    return inflater.inflate(layoutRes, this, false)
}