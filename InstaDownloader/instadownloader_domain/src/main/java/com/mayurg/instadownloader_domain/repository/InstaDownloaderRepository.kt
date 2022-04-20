package com.mayurg.instadownloader_domain.repository

import android.net.Uri
import java.io.File

interface InstaDownloaderRepository {

    suspend fun downloadMedia(url: String)

    suspend fun readFiles(): MutableList<File>

    suspend fun saveFilesUri(uri: Uri)

    suspend fun getFilesUri(): Uri?
}