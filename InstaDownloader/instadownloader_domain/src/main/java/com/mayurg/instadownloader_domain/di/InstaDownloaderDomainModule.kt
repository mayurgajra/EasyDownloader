package com.mayurg.instadownloader_domain.di

import com.mayurg.instadownloader_domain.repository.InstaDownloaderRepository
import com.mayurg.instadownloader_domain.use_case.InstaGetDirectoryUri
import com.mayurg.instadownloader_domain.use_case.InstaLoadFiles
import com.mayurg.instadownloader_domain.use_case.InstaLoaderUseCases
import com.mayurg.instadownloader_domain.use_case.InstaSaveDirectoryUri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InstaDownloaderDomainModule {

    @Provides
    @ViewModelScoped
    fun provideInstaUseCases(
        instaLoadRepository: InstaDownloaderRepository
    ): InstaLoaderUseCases {
        return InstaLoaderUseCases(
            InstaLoadFiles(instaLoadRepository),
            InstaSaveDirectoryUri(instaLoadRepository),
            InstaGetDirectoryUri(instaLoadRepository),
        )
    }
}