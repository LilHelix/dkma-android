package com.helix.dontkillmyapp.presentation.theme

interface ThemeHelper {

    fun changeTheme(theme: Theme)
}

enum class Theme {
    DAY, NIGHT, DEFAULT;
}