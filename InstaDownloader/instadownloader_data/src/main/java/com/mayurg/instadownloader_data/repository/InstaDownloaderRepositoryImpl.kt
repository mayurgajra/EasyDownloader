package com.mayurg.instadownloader_data.repository

import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.work.*
import com.mayurg.filemanager.FileManager
import com.mayurg.instadownloader_data.remote.DownloadWorker
import com.mayurg.instadownloader_data.remote.InstaDownloaderApi
import com.mayurg.instadownloader_domain.repository.InstaDownloaderRepository
import javax.inject.Inject
import javax.inject.Named


class InstaDownloaderRepositoryImpl @Inject constructor(
    @Named("response") private val response: String,
    private val instaParser: InstaParser,
    private val workManager: WorkManager,
    private val instaDownloaderApi: InstaDownloaderApi,
    private val fileManager: FileManager
) : InstaDownloaderRepository {

    override suspend fun downloadMedia(url: String) {

        // TODO : Uncomment this When ready to make actual API call
        val a = url.substring(0, url.lastIndexOf("/"))
        val b = "https://instagram85.p.rapidapi.com/media/$a"
        val response1 = instaDownloaderApi.getMediaInfoFromUrl(b, "url")
        Log.d("MG-data", response1.toString())

        val downloadUrl = instaParser.getDownloadUrl(response1.body()!!.string())

        val data = Data.Builder()
        data.putString("downloadUrl", downloadUrl)

        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED
                    )
                    .build()
            )
            .setInputData(data.build())
            .build()

        workManager
            .beginUniqueWork(
                "download",
                ExistingWorkPolicy.KEEP,
                downloadRequest
            )
            .enqueue()
    }

    override suspend fun readFiles(uri: Uri): List<DocumentFile> {
        return fileManager.loadDirectory(uri)
    }
}