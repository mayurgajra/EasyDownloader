package com.mayurg.instadownloader_domain.use_case

import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.mayurg.instadownloader_domain.repository.InstaDownloaderRepository
import javax.inject.Inject

class InstaLoadFiles @Inject constructor(
    private val instaDownloaderRepository: InstaDownloaderRepository
) {

    suspend operator fun invoke(uri: Uri): List<DocumentFile> {
        return instaDownloaderRepository.readFiles(uri)
    }
}