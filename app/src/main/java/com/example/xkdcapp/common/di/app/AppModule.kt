package com.example.xkdcapp.common.di.app

import com.example.xkdcapp.Constants.BASE_URL_XKCD
import com.example.xkdcapp.Constants.BASE_URL_XKCD_DETAIL
import com.example.xkdcapp.common.di.Retrofit1
import com.example.xkdcapp.common.di.Retrofit2
import com.example.xkdcapp.networking.ComicApi
import com.example.xkdcapp.networking.ComicDetailApi
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
    @Retrofit1
    fun retrofit1(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_XKCD)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    @Retrofit2
    fun retrofit2(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_XKCD_DETAIL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun comicApi(@Retrofit1 retrofit: Retrofit) = retrofit.create(ComicApi::class.java)

    @Provides
    @AppScope
    fun comicDetailsApi(@Retrofit2 retrofit: Retrofit) = retrofit.create(ComicDetailApi::class.java)
}