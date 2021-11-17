package com.example.mychallenge.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.mychallenge.common.Keys.Companion.APP_SETTINGS_FILE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharePreferencesModule {

    @Singleton
    @Provides
    fun provideSharePreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE)
}