package com.mayurg.instadownloader_presentation

import androidx.documentfile.provider.DocumentFile

data class InstaListState(
    val list: List<DocumentFile> = mutableListOf()
)
