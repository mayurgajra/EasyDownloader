package com.mayurg.instadownloader_presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun InstagramTab() {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        FilesList(messages = listOf(InstaFile("cdc")))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesList(messages: List<InstaFile>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        content = {
            items(10) { item ->
                Image(
                    painter = painterResource(id = R.drawable.img),
                    modifier = Modifier
                        .border(0.5.dp, Color.White)
                        .aspectRatio(1f),
                    contentDescription = "test",
                    contentScale = ContentScale.Crop
                )
            }
        }
    )
}