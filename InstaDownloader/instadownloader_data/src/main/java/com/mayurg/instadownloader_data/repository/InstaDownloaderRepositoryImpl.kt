package com.mayurg.instadownloader_data.repository

import android.util.Log
import androidx.work.*
import com.mayurg.filemanager.FileManager
import com.mayurg.instadownloader_data.remote.DownloadWorker
import com.mayurg.instadownloader_data.remote.InstaDownloaderApi
import com.mayurg.instadownloader_domain.repository.InstaDownloaderRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Named


class InstaDownloaderRepositoryImpl @Inject constructor(
    @Named("response") private val response: String,
    private val instaParser: InstaParser,
    private val workManager: WorkManager,
    private val instaDownloaderApi: InstaDownloaderApi,
    private val fileManager: FileManager,
) : InstaDownloaderRepository {

    override suspend fun downloadMedia(url: String) {

        val a = url.substring(0, url.lastIndexOf("/"))
        val b = "https://instagram85.p.rapidapi.com/media/$a"
        val response1 = instaDownloaderApi.getMediaInfoFromUrl(b, "url")
        Log.d("MG-data", response1.toString())

        response1.body()?.string()?.let { body ->
            val downloadUrls = instaParser.getDownloadUrl(body)
            val type = instaParser.getType(body)

            for (i in downloadUrls.indices) {
                val downloadUrl = downloadUrls[i]
                val data = Data.Builder()
                data.putString("downloadUrl", downloadUrl)
                data.putString("type", type)

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
                        "download $i",
                        ExistingWorkPolicy.KEEP,
                        downloadRequest
                    )
                    .enqueue()
            }
        }


    }

    override suspend fun readFiles(): MutableList<File> {
        val path = "/Easydownloader/Insta/"
        return fileManager.loadInstaFiles(path)
    }
}