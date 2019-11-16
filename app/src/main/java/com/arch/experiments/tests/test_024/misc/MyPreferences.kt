package com.arch.experiments.tests.test_024.misc

import android.content.Context
import com.arch.experiments.R
import timber.log.Timber

class MyPreferences(val context: Context) {
    private val preferences = context.getSharedPreferences(
        context.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )

    private val key_name = "name"
    private val key_click = "click"

    fun setName(name: String) {
        Timber.d("TESTING setName: $name")
        preferences.edit().putString(key_name, name).apply()
    }

    fun getName(): String = preferences.getString(key_name, "no_name") ?: "no_name"

    fun incrementClickCount() {
        var count = preferences.getInt(key_click, 0)
        count++
        Timber.d("TESTING incrementClickCount: $count")
        preferences.edit().putInt(key_click, count).apply()
    }
}