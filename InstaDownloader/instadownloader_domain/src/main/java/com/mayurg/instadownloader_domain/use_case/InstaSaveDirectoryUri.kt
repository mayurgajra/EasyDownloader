package com.mayurg.instadownloader_domain.use_case

import android.net.Uri
import com.mayurg.instadownloader_domain.repository.InstaDownloaderRepository
import javax.inject.Inject

class InstaSaveDirectoryUri @Inject constructor(
    private val instaDownloaderRepository: InstaDownloaderRepository
) {

    suspend operator fun invoke(uri: Uri) {
        instaDownloaderRepository.saveFilesUri(uri)
    }
}