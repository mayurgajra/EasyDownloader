package com.mayurg.fbdownloader_domain.repository

import java.io.File

interface FbDownloaderRepository {

    suspend fun downloadMedia(url: String)

    suspend fun readFiles(): MutableList<File>
}