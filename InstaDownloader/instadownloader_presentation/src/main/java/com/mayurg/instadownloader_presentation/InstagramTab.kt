package com.mayurg.instadownloader_presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        cells = GridCells.Fixed(2),
        content = {
            items(10) { item ->
                Text(text = "Item $item", modifier = Modifier.height(250.dp))
            }
        }
    )
}