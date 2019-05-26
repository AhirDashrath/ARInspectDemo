package com.arinspect.facts.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceHelper {
    private lateinit var pref: SharedPreferences
    private const val pref_name = "title"
    private const val titleKey = "titleKey"

    fun initPreference(context: Context) {
        pref = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE)
    }

    fun storeTitle(title: String) {
        val prefsEditor: SharedPreferences.Editor = pref.edit()
        with(prefsEditor) {
            putString(titleKey, title)
            commit()
        }
    }

    fun getTitle(): String? {
        return pref.getString(titleKey, "Testing")
    }
}