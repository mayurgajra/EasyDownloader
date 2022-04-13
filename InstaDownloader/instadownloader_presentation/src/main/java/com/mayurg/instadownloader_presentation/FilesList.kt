package com.mayurg.instadownloader_presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesList(list: List<DocumentFile>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        content = {
            items(list) { item ->
                if (item.name?.contains(".mp4") == true) {
                    VideoThumbnailLoader(
                        uri = item.uri
                    )
                } else {
                    AsyncImage(
                        model = item.uri,
                        modifier = Modifier
                            .border(0.5.dp, Color.White)
                            .aspectRatio(1f),
                        contentDescription = "test",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    )
}