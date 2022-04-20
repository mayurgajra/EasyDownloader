package com.mayurg.instadownloader_domain.use_case

import com.mayurg.instadownloader_domain.repository.InstaDownloaderRepository
import java.io.File
import javax.inject.Inject

class InstaLoadFiles @Inject constructor(
    private val instaDownloaderRepository: InstaDownloaderRepository
) {

    suspend operator fun invoke(): MutableList<File> {
        return instaDownloaderRepository.readFiles()
    }
}