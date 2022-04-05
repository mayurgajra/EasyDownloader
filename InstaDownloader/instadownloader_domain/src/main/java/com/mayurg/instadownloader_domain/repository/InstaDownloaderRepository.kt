package com.mayurg.instadownloader_domain.repository

import android.net.Uri
import androidx.documentfile.provider.DocumentFile

interface InstaDownloaderRepository {

    suspend fun downloadMedia(url: String)

    suspend fun readFiles(uri: Uri): List<DocumentFile>
}