package com.mayurg.instadownloader_data.local

import android.content.SharedPreferences
import android.net.Uri
import com.mayurg.instadownloader_data.utils.Constants
import javax.inject.Inject
import javax.inject.Named

class InstaDownloaderPrefsImpl @Inject constructor(
    @Named(Constants.PREF_NAME) private val sharedPreferences: SharedPreferences
) : InstaDownloaderPrefs {

    override suspend fun saveFilesUri(uri: Uri) {
        sharedPreferences.edit()
            .putString(Constants.PREF_DIRECTORY_URI_KEY, uri.toString()).apply()
    }

    override suspend fun getFilesUri(): Uri? {
        try {
            return Uri.parse(sharedPreferences.getString(Constants.PREF_DIRECTORY_URI_KEY, null))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}