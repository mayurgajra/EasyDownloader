package com.mayurg.instadownloader_data.local

import android.net.Uri

interface InstaDownloaderPrefs {

    suspend fun saveFilesUri(uri: Uri)

    suspend fun getFilesUri(): Uri?
}