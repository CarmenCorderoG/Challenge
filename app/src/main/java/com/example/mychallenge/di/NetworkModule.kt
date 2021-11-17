package com.example.mychallenge.di

import android.app.Application
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import com.example.mychallenge.common.Keys
import com.example.mychallenge.data.network.IPCApiService
import com.example.mychallenge.data.network.TopTensApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit
        .Builder()
        .baseUrl(Keys.BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideIPCApiService(retrofit: Retrofit): IPCApiService =
        retrofit.create(IPCApiService::class.java)

    @Provides
    @Singleton
    fun provideTopTensApiService(retrofit: Retrofit): TopTensApiService =
        retrofit.create(TopTensApiService::class.java)

}
