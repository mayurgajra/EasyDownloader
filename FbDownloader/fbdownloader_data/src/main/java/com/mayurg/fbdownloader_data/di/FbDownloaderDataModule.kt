package com.mayurg.fbdownloader_data.di

import android.app.Application
import androidx.work.WorkManager
import com.mayurg.fbdownloader_data.R
import com.mayurg.fbdownloader_data.remote.FbDownloaderApi
import com.mayurg.fbdownloader_data.repository.FBParser
import com.mayurg.fbdownloader_data.repository.FbDownloaderRepositoryImpl
import com.mayurg.fbdownloader_domain.repository.FbDownloaderRepository
import com.mayurg.filemanager.FileManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FbDownloaderDataModule {

    @Provides
    @Singleton
    @Named("fbOkHttpClient")
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
    fun providesInstaDownloaderApi(
        @Named("fbOkHttpClient") client: OkHttpClient
    ): FbDownloaderApi {
        return Retrofit.Builder()
            .baseUrl(FbDownloaderApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(FbDownloaderApi::class.java)
    }

    @Provides
    @Singleton
    @Named("fbresponse")
    fun providesSampleResponse(
        app: Application
    ): String {
        return app.resources
            .openRawResource(R.raw.fbsample)
            .readBytes()
            .decodeToString()
    }

    @Provides
    @Singleton
    @Named("fbWorkManager")
    fun provideWorkManager(
        app: Application
    ): WorkManager {
        return WorkManager.getInstance(app)
    }

    @Provides
    @Singleton
    @Named("fbFileManager")
    fun provideFileManger(
        app: Application
    ): FileManager {
        return FileManager.Builder(app.applicationContext).build()
    }


    @Provides
    @Singleton
    fun provideFBRepository(
        @Named("fbresponse") response: String,
        fbParser: FBParser,
        @Named("fbWorkManager") workManager: WorkManager,
        fbDownloaderApi: FbDownloaderApi,
        @Named("fbFileManager") fileManager: FileManager,
    ): FbDownloaderRepository {
        return FbDownloaderRepositoryImpl(
            response,
            fbParser,
            workManager,
            fbDownloaderApi,
            fileManager,
        )
    }
}