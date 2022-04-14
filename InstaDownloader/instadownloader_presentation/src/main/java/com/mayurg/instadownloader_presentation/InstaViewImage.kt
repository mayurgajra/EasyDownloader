package com.mayurg.instadownloader_presentation

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ViewImage(uri: Uri) {
    AsyncImage(
        model = uri,
        modifier = Modifier
            .border(0.5.dp, Color.White)
            .aspectRatio(1f),
        contentDescription = "view",
        contentScale = ContentScale.Crop,
    )
}