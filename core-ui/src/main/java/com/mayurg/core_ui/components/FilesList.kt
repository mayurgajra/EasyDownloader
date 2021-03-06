package com.mayurg.core_ui.components

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.core.net.toUri
import coil.compose.AsyncImage
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesList(list: MutableList<File>, onItemClick: (uri: Uri) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        content = {
            items(list) { item ->
                if (item.name.contains(".mp4")) {
                    VideoThumbnailLoader(
                        uri = item.toUri(),
                        modifier = Modifier
                            .border(0.5.dp, Color.White)
                            .aspectRatio(1f)
                            .clickable {
                                onItemClick(item.toUri())
                            }
                    )
                } else {
                    AsyncImage(
                        model = item.toUri(),
                        modifier = Modifier
                            .border(0.5.dp, Color.White)
                            .aspectRatio(1f)
                            .clickable {
                                onItemClick(item.toUri())
                            },
                        contentDescription = "test",
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    )
}