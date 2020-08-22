package com.helix.dontkillmyapp.navigation

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.terrakok.cicerone.android.support.FragmentParams
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command

class CustomAppNavigator(activity: FragmentActivity, @IdRes containerId: Int) : SupportAppNavigator(activity, containerId) {

    override fun applyCommand(command: Command) {
        when (command) {
            is ShowAtop -> fragmentShowAtop(command)
            else -> super.applyCommand(command)
        }
    }

    private fun fragmentShowAtop(command: ShowAtop) {
        val screen = command.screen

        val fragmentParams = screen.fragmentParams
        val fragment = if (fragmentParams == null) createFragment(screen) else null

        showAtopFragmentInternal(command, screen, fragmentParams, fragment)
    }

    private fun showAtopFragmentInternal(
        command: ShowAtop,
        screen: SupportAppScreen,
        fragmentParams: FragmentParams?,
        fragment: Fragment?
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment,
            fragmentTransaction
        )

        when {
            fragmentParams != null -> {
                fragmentTransaction.add(
                    containerId,
                    fragmentParams.fragmentClass,
                    fragmentParams.arguments
                )
            }
            fragment != null -> {
                fragmentTransaction.add(containerId, fragment)
            }
            else -> {
                throw IllegalArgumentException(
                    "Either 'params' or 'fragment' shouldn't " +
                            "be null for " + screen.screenKey
                )
            }
        }

        fragmentTransaction
            .addToBackStack(screen.screenKey)
            .commit()

        localStackCopy.add(screen.screenKey)
    }
}