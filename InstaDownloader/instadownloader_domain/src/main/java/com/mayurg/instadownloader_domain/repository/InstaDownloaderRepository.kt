package com.mayurg.instadownloader_domain.repository

interface InstaDownloaderRepository {

    suspend fun downloadMedia(url: String)
}