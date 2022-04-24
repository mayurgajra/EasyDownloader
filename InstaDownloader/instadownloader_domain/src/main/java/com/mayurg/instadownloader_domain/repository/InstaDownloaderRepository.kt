package com.mayurg.instadownloader_domain.repository

import java.io.File

interface InstaDownloaderRepository {

    suspend fun downloadMedia(url: String)

    suspend fun readFiles(): MutableList<File>
}