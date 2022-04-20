package com.mayurg.instadownloader_presentation

import java.io.File

data class InstaListState(
    val list: MutableList<File> = mutableListOf()
)
