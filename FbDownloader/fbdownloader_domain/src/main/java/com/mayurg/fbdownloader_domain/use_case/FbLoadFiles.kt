package com.mayurg.fbdownloader_domain.use_case

import com.mayurg.fbdownloader_domain.repository.FbDownloaderRepository
import java.io.File
import javax.inject.Inject

class FbLoadFiles @Inject constructor(
    private val fbDownloaderRepository: FbDownloaderRepository
) {

    suspend operator fun invoke(): MutableList<File> {
        return fbDownloaderRepository.readFiles()
    }
}