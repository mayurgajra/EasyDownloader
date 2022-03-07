package com.mayurg.easydownloader.services

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.mayurg.easydownloader.utils.CHANNEL_ID
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

        when (val act = intent?.action) {
            CUSTOM_ACTION -> {
                val builder = createReplyNotification()
                NotificationManagerCompat.from(this).apply {
                    notify(NOTIFICATION_ID, builder.build())
                }

                val text = getMessageText(intent)
                text?.toString()?.let { url ->
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        val b = "https://api.instagram.com/oembed"
                        val data = instaDownloaderApi.getMediaInfoFromUrl(b, url)
                        val downloadUrl = data.body()!!.thumbnail_url!!
                        val thumbUrl = data.body()?.thumbnail_url?.let {
                            it.substring(0, it.indexOfFirst { c -> c == '?' })
                        }.orEmpty()
                        download(downloadUrl,thumbUrl)
                        Log.d("MG-data", data.toString())
                    }
                }

            }

            else -> {
                startForegroundService()
            }
        }

        return super.onStartCommand(intent, flags, startId)

    }

    private fun download(url: String, thumbUrl: String) {
        if (!Patterns.WEB_URL.matcher(url).matches())
            return

        val fileName = thumbUrl.substring(thumbUrl.lastIndexOf("/") + 1, thumbUrl.length)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(fileName)
            .setDescription("cdcd")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

        val id = downloadManager.enqueue(request)
    }

    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)
            ?.getCharSequence(KEY_TEXT_REPLY)
    }

    fun startForegroundService() {
        createNotificationChannel()


        var builder = createReplyNotification()

        startForeground(102, builder.build())
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Download"
            val descriptionText = "Download service"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                setShowBadge(false)
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}