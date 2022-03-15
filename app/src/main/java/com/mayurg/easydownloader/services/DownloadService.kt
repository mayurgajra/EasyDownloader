package com.mayurg.easydownloader.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.mayurg.easydownloader.utils.CHANNEL_ID
import com.mayurg.easydownloader.utils.KEY_TEXT_REPLY
import com.mayurg.easydownloader.utils.createReplyNotification
import com.mayurg.instadownloader_domain.repository.InstaDownloaderRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DownloadService : LifecycleService() {

    @Inject
    lateinit var instaDownloaderApi: InstaDownloaderRepository

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
                        instaDownloaderApi.downloadMedia(url)
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