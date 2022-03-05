package com.mayurg.instadownloader_data.models


import com.squareup.moshi.Json

data class Item(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "image_versions2")
    val imageVersions2: ImageVersions2
)