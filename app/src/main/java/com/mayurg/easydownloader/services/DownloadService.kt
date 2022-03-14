package com.mayurg.easydownloader.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.mayurg.easydownloader.R
import com.mayurg.easydownloader.utils.CHANNEL_ID
import com.mayurg.easydownloader.utils.InstaParser
import com.mayurg.easydownloader.utils.KEY_TEXT_REPLY
import com.mayurg.easydownloader.utils.createReplyNotification
import com.mayurg.instadownloader_data.remote.InstaDownloaderApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DownloadService : LifecycleService() {

    @Inject
    lateinit var instaDownloaderApi: InstaDownloaderApi

    companion object {
        const val CUSTOM_ACTION = "com.mayurg.easydownloader.download"
        const val NOTIFICATION_ID = 102
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            CUSTOM_ACTION -> {
                val builder = createReplyNotification()
                NotificationManagerCompat.from(this).apply {
                    notify(NOTIFICATION_ID, builder.build())
                }

                val text = getMessageText(intent)
                text?.toString()?.let { url ->
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {

                        val response = resources
                            .openRawResource(R.raw.sample)
                            .readBytes()
                            .decodeToString()

                        val downloadUrl = InstaParser.getDownloadUrl(response)

                        Log.d("MG-downloadUrl", downloadUrl)


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


                        val workManager = WorkManager.getInstance(applicationContext)

                        workManager
                            .beginUniqueWork(
                                "download",
                                ExistingWorkPolicy.KEEP,
                                downloadRequest
                            )
                            .enqueue()
                        /* val a = url.substring(0, url.lastIndexOf("/"))
                         val b = "https://instagram85.p.rapidapi.com/media/$a"
                         val data = instaDownloaderApi.getMediaInfoFromUrl(b, "url")
                         Log.d("MG-data", data.toString())*/
                    }
                }
            }

            else -> {
                startForegroundService()
            }
        }

        return super.onStartCommand(intent, flags, startId)

    }


    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)
            ?.getCharSequence(KEY_TEXT_REPLY)
    }

    private fun startForegroundService() {
        createNotificationChannel()

        val builder = createReplyNotification()

        startForeground(102, builder.build())
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Download"
            val descriptionText = "Download service"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                setShowBadge(false)
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}