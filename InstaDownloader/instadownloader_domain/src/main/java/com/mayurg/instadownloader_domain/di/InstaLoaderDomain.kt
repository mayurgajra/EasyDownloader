package com.mayurg.instadownloader_domain.di

import com.mayurg.instadownloader_domain.repository.InstaDownloaderRepository
import com.mayurg.instadownloader_domain.use_case.InstaLoadFiles
import com.mayurg.instadownloader_domain.use_case.InstaLoaderUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InstaLoaderDomain {

    @Provides
    @ViewModelScoped
    fun provideInstaUseCases(
        instaLoadRepository: InstaDownloaderRepository
    ): InstaLoaderUseCases {
        return InstaLoaderUseCases(InstaLoadFiles(instaLoadRepository))
    }
}