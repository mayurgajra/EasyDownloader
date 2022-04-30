package com.mayurg.fbdownloader_data.repository

import androidx.work.*
import com.mayurg.fbdownloader_data.remote.DownloadWorker
import com.mayurg.fbdownloader_data.remote.FbDownloaderApi
import com.mayurg.fbdownloader_domain.repository.FbDownloaderRepository
import com.mayurg.filemanager.FileManager
import java.io.File
import javax.inject.Inject
import javax.inject.Named


class FbDownloaderRepositoryImpl @Inject constructor(
    @Named("fbresponse") private val response: String,
    private val fbParser: FBParser,
    @Named("fbWorkManager") private val workManager: WorkManager,
    private val fbDownloaderApi: FbDownloaderApi,
    @Named("fbFileManager") private val fileManager: FileManager,
) : FbDownloaderRepository {

    override suspend fun downloadMedia(url: String) {

        /*  val a = url.substring(0, url.lastIndexOf("/"))
          val b = "https://instagram85.p.rapidapi.com/media/$a"
          val response1 = fbDownloaderApi.getMediaInfoFromUrl(b)
          Log.d("MG-data", response1.toString())*/

        response.let { body ->
            val downloadUrls = fbParser.getDownloadUrl(body)
            val type = "video"

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
        val path = "/Easydownloader/FB/"
        return fileManager.loadInstaFiles(path)
    }
}