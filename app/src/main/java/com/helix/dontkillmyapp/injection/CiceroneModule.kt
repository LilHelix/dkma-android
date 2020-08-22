package com.helix.dontkillmyapp.injection

import com.helix.dontkillmyapp.navigation.CustomRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.terrakok.cicerone.Cicerone

@Module
@InstallIn(ApplicationComponent::class)
object CiceroneModule {

    private val customRouter = CustomRouter()
    private val cicerone = Cicerone.create(customRouter)

    @Provides
    fun provideRouter() = customRouter

    @Provides
    fun provideNavHolder() = cicerone.navigatorHolder
}