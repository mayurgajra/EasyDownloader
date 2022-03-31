package com.mayurg.instadownloader_presentation

import android.app.Application
import com.mayurg.filemanager.FileManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    @Singleton
    fun provideFileManger(
        app: Application
    ): FileManager {
        return FileManager.Builder(app.applicationContext).build()
    }
}