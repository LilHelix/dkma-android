package com.helix.dontkillmyapp.navigation

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CustomRouter : Router() {

    fun showAtop(screen: SupportAppScreen) {
        executeCommands(ShowAtop(screen))
    }
}