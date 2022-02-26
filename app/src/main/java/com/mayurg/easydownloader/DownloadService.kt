package com.mayurg.easydownloader

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.lifecycle.LifecycleService

class DownloadService : LifecycleService() {

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
                    download(url)
                }

            }

            else -> {
                startForegroundService()
            }
        }

        return super.onStartCommand(intent, flags, startId)

    }

    private fun download(url: String) {
        if (!Patterns.WEB_URL.matcher(url).matches())
            return

        val fileName = url.substring(url.lastIndexOf("/") + 1, url.length)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        request.allowScanningByMediaScanner()
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