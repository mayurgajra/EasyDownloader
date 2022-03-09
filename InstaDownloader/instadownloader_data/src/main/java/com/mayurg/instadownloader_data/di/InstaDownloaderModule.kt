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
            ).addInterceptor {
                it.proceed(
                    it.request().newBuilder()
                        .addHeader("x-rapidapi-host", "instagram85.p.rapidapi.com")
                        .addHeader(
                            "x-rapidapi-key",
                            "393dc2581cmsh4136cd415472ca0p1a1259jsn911f834c48ea"
                        )
                        .build()
                )
            }.build()
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