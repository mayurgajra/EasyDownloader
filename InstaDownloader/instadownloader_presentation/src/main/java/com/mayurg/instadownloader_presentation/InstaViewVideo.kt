package com.mayurg.instadownloader_presentation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
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

    LaunchedEffect(key1 = uri){
        videoPlayerController.setSource(VideoPlayerSource.LocalStorage(uri))
    }

    VideoPlayer(
        videoPlayerController = videoPlayerController,
        backgroundColor = Color.Transparent,
        modifier = Modifier.fillMaxSize(),
        controlsEnabled = true
    )
}