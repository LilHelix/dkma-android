package com.helix.dontkillmyapp.presentation.theme

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.helix.dontkillmyapp.data.prefs.Preferences
import javax.inject.Inject

class ThemeHelperImpl @Inject constructor(
    private val preferences: Preferences
) : ThemeHelper {

    init {
        changeTheme(preferences.theme)
    }

    override fun changeTheme(theme: Theme) {
        preferences.theme = theme
        when (theme) {
            Theme.DAY -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.NIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Theme.DEFAULT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
            }
        }
    }
}