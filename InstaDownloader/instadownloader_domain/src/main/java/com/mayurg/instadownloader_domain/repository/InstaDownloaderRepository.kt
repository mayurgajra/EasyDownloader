package com.mayurg.instadownloader_domain.repository

interface InstaDownloaderRepository {

    fun downloadMedia(url: String)
}