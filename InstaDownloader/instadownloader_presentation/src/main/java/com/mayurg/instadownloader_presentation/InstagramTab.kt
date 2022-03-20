package com.mayurg.instadownloader_presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InstagramTab() {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        FilesList(messages = listOf(InstaFile("cdc")))
    }
}

@Composable
fun FilesList(messages: List<InstaFile>) {
    LazyColumn {
        items(messages) { message ->
            Text(text = "Insta File ${message.name}")
        }
    }
}