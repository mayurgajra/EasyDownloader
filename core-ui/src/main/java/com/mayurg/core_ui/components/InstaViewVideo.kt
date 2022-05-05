package com.mayurg.core_ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.halilibo.composevideoplayer.VideoPlayer
import com.halilibo.composevideoplayer.VideoPlayerSource
import com.halilibo.composevideoplayer.rememberVideoPlayerController

@Composable
fun ViewVideo(uri: Uri) {
    val videoPlayerController = rememberVideoPlayerController()
    val videoPlayerUiState by videoPlayerController.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(videoPlayerController, lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                videoPlayerController.pause()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = uri) {
        videoPlayerController.setSource(VideoPlayerSource.LocalStorage(uri))
    }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        VideoPlayer(
            videoPlayerController = videoPlayerController,
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            controlsEnabled = true
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    shareVideo(context, uri)
                }) {
                Icon(imageVector = Icons.Filled.Share, contentDescription = "")
            }
        }
    }


}

private fun shareVideo(context: Context, uri: Uri) {

    val photoURI: Uri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName.toString() + ".provider",
        uri.toFile()
    )

    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "video/*"
    sharingIntent.setPackage("com.whatsapp")
    sharingIntent.putExtra(Intent.EXTRA_STREAM, photoURI)
    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)


    context.startActivities(arrayOf(Intent.createChooser(sharingIntent, "Share with")))
}