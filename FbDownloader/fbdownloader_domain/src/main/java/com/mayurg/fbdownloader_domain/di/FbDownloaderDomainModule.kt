package com.mayurg.fbdownloader_domain.di

import com.mayurg.fbdownloader_domain.repository.FbDownloaderRepository
import com.mayurg.fbdownloader_domain.use_case.FbLoadFiles
import com.mayurg.fbdownloader_domain.use_case.FbLoaderUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FbDownloaderDomainModule {

    @Provides
    @ViewModelScoped
    fun provideInstaUseCases(
        fbDownloaderRepository: FbDownloaderRepository
    ): FbLoaderUseCases {
        return FbLoaderUseCases(
            FbLoadFiles(fbDownloaderRepository),
        )
    }
}