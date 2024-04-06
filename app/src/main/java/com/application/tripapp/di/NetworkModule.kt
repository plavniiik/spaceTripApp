package com.application.tripapp.di

import android.os.Build
import com.application.tripapp.network.Api
import com.google.firebase.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideApi(): Api {
        return Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .client(
                OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                ).addInterceptor(Interceptor { chain ->
                    val request = chain.request()
                    val url: HttpUrl = request.url.newBuilder().addQueryParameter("api_key", com.application.tripapp.BuildConfig.NASA_API_KEY).build()
                    val newRequest = request.newBuilder().url(url).build()
                    chain.proceed(newRequest)
                })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}