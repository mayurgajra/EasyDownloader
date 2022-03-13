package com.mayurg.easydownloader.services

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mayurg.easydownloader.R
import com.mayurg.instadownloader_data.remote.InstaDownloaderApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

@Suppress("BlockingMethodInNonBlockingContext")
class DownloadWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var instaDownloaderApi: InstaDownloaderApi

    override suspend fun doWork(): Result {
        startForegroundService()
        val response = instaDownloaderApi.downloadInstaImage("https://scontent-frx5-2.cdninstagram.com/v/t51.2885-15/275513200_122171373524205_6779244514128294598_n.jpg?stp=dst-jpg_e15_fr_s1080x1080&_nc_ht=scontent-frx5-2.cdninstagram.com&_nc_cat=109&_nc_ohc=TwI-hzxnQFEAX-23qvo&edm=AABBvjUBAAAA&ccb=7-4&oh=00_AT-BdeKYk0EWm9rpFOj20pev-2i_rkmTRYADWlzg1qhT1Q&oe=62348B28&_nc_sid=83d603")
        response.body()?.let { body ->
            return withContext(Dispatchers.IO) {
                val file = File(context.cacheDir, "image.jpg")
                val outputStream = FileOutputStream(file)
                outputStream.use { stream ->
                    try {
                        stream.write(body.bytes())
                    } catch(e: IOException) {
                        return@withContext Result.failure(
                            workDataOf(
                               "error" to e.localizedMessage
                            )
                        )
                    }
                }
                Result.success(
                    workDataOf(
                        "uri" to file.toUri().toString()
                    )
                )
            }
        }
        if(!response.isSuccessful) {
            if(response.code().toString().startsWith("5")) {
                return Result.retry()
            }
            return Result.failure(
                workDataOf(
                    "error" to "Network error"
                )
            )
        }
        return Result.failure(
            workDataOf("error" to "Unknown error")
        )
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, "download_channel")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText("Downloading...")
                    .setContentTitle("Download in progress")
                    .build()
            )
        )
    }
}