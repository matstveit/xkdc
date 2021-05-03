package com.example.xkdcapp.common.di.app

import com.example.xkdcapp.Constants.BASE_URL_XKCD
import com.example.xkdcapp.networking.ComicApi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @AppScope
    @Provides
    fun httpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor { message ->
            Timber.i(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Provides
    @AppScope
    fun retrofit1(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_XKCD)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun comicApi(retrofit: Retrofit) = retrofit.create(ComicApi::class.java)
}