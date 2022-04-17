package com.mayurg.instadownloader_presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.util.DebugLogger

@Composable
fun VideoThumbnailLoader(
    uri: Uri,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context).logger(DebugLogger()).build()
    val model =
        ImageRequest.Builder(context = context)
            .data(data = uri)
            .apply(block = {
                decoderFactory(VideoFrameDecoder.Factory())
                listener(onError = { request, result -> println(result.throwable.message + "Error") },
                    onStart = { println("started") })
            }).build()

    Box {
        Image(
            modifier = modifier,
            painter = rememberAsyncImagePainter(model, imageLoader = imageLoader),
            contentDescription = "Video thumb",
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play")
        }
    }

    LaunchedEffect(key1 = true) {
        imageLoader.execute(model)
    }
}