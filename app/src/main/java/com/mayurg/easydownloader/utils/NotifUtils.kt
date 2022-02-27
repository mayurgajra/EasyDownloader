package com.mayurg.easydownloader.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.mayurg.easydownloader.R
import com.mayurg.easydownloader.services.DownloadService
import com.mayurg.easydownloader.services.DownloadService.Companion.CUSTOM_ACTION

const val CHANNEL_ID = "DownloadService"
const val KEY_TEXT_REPLY = "key_text_reply"
const val PENDING_INTENT_CODE = 202

fun Context.createReplyNotification(): NotificationCompat.Builder {
    val replyLabel = "Paste"
    val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
        setLabel("Paste insta or fb url")
        build()
    }

    val replyIntent = Intent(this, DownloadService::class.java)
    replyIntent.action = CUSTOM_ACTION

    val replyPendingIntent: PendingIntent =
        PendingIntent.getService(
            this,
            PENDING_INTENT_CODE,
            replyIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

    // Create the reply action and add the remote input.
    val action: NotificationCompat.Action =
        NotificationCompat.Action.Builder(
            null,
            replyLabel,
            replyPendingIntent
        ).addRemoteInput(remoteInput).build()

    return NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_vector_download)
        .setContentTitle("Download service running")
        .setContentText("Paste url to download")
        .addAction(action)
        .setOngoing(true)
        .setAutoCancel(false)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
}