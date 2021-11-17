package com.example.mychallenge.di

import android.app.Application
import androidx.room.Room
import com.example.mychallenge.data.local.database.UserRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun providerRoomDataBase(app: Application): UserRoomDataBase = Room.databaseBuilder(
        app, UserRoomDataBase::class.java, "user_database").build()
}