package com.mayurg.fbdownloader_data.di

import com.mayurg.fbdownloader_data.remote.FbDownloaderApi
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
object FbDownloaderDataModule {

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
                        .addHeader("x-rapidapi-host", "facebook17.p.rapidapi.com")
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
    fun providesInstaDownloaderApi(client: OkHttpClient): FbDownloaderApi {
        return Retrofit.Builder()
            .baseUrl(FbDownloaderApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(FbDownloaderApi::class.java)
    }

}