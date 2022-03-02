package com.mayurg.instadownloader_data.di

import com.mayurg.instadownloader_data.remote.InstaDownloaderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InstaDownloaderModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }


    @Provides
    @Singleton
    fun providesInstaDownloaderApi(client: OkHttpClient): InstaDownloaderApi {
        return Retrofit.Builder()
            .baseUrl(InstaDownloaderApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(InstaDownloaderApi::class.java)
    }
}