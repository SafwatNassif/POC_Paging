package com.example.pocpaging.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject


class SharedPrefUtils @Inject constructor(private val pref: SharedPreferences) {

    private fun putString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    private fun getString(key: String) = pref.getString(key, "")

    private fun putInt(key: String, value: Int) {
        pref.edit().putInt(key, value).apply()
    }

    private fun getInt(key: String) = pref.getInt(key, 0)


    fun setName(name: String) {
        putString("name", name)
    }


    companion object {
        const val PREF_FILE = "shared_preference_file_store"
    }
}