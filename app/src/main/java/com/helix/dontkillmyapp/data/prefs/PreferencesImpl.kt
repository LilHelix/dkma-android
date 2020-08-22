package com.helix.dontkillmyapp.data.prefs

import android.app.Application
import android.content.Context
import com.helix.dontkillmyapp.presentation.theme.Theme
import javax.inject.Inject

class PreferencesImpl @Inject constructor(
    private val application: Application
) : Preferences {

    companion object {
        private const val PREFS_NAME = "dkma_prefs"
        private const val KEY_CURRENT_THEME = "current_theme"
    }

    private val sharedPreferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var theme: Theme
        get() {
            val currentThemeEnumName =
                sharedPreferences.getString(KEY_CURRENT_THEME, Theme.DAY.name) ?: return Theme.DAY
            return Theme.valueOf(currentThemeEnumName)
        }
        set(newTheme) {
            sharedPreferences.edit().putString(KEY_CURRENT_THEME, newTheme.name).apply()
        }
}