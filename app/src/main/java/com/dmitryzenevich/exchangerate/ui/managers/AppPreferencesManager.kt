package com.dmitryzenevich.exchangerate.ui.managers

import android.content.SharedPreferences
import androidx.core.content.edit
import com.dmitryzenevich.exchangerate.ui.utils.SORT_ALPHABET_ASC
import javax.inject.Inject

class AppPreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val BASE_KEY = "base"
        const val SORT_KEY = "sort"
    }

    var base: String
        set(value) = sharedPreferences.edit { putString(BASE_KEY, value) }
        get() = sharedPreferences.getString(BASE_KEY, "EUR") ?: "EUR"

    var sort: Int
        set(value) = sharedPreferences.edit { putInt(SORT_KEY, value) }
        get() = sharedPreferences.getInt(SORT_KEY, SORT_ALPHABET_ASC)
}