package com.helix.dontkillmyapp.injection

import android.app.Application
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html
import android.util.Size
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawableWrapper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.helix.dontkillmyapp.R
import com.helix.dontkillmyapp.data.prefs.Preferences
import com.helix.dontkillmyapp.data.prefs.PreferencesImpl
import com.helix.dontkillmyapp.presentation.theme.ThemeHelper
import com.helix.dontkillmyapp.presentation.theme.ThemeHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(ApplicationComponent::class)
object MiscModule {

    @Provides
    @Singleton
    fun provideGlideImageLoader(application: Application): Html.ImageGetter = Html.ImageGetter {
        ContextCompat.getDrawable(application, R.drawable.image_loader_empty_stub)
    }

    @Provides
    @Singleton
    fun providePreferences(application: Application): Preferences = PreferencesImpl(application)

    @Provides
    @Singleton
    fun provideThemeHelper(preferences: Preferences): ThemeHelper = ThemeHelperImpl(preferences)
}
