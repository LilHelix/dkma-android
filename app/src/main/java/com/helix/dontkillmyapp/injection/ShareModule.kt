package com.helix.dontkillmyapp.injection

import com.helix.dontkillmyapp.presentation.share.ShareHelper
import com.helix.dontkillmyapp.presentation.share.ShareHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class ShareModule {

    @Binds
    @ActivityScoped
    abstract fun provideShareHelper(shareHelperImpl: ShareHelperImpl): ShareHelper
}