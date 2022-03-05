package com.mayurg.instadownloader_data.models


import com.squareup.moshi.Json

data class Candidate(
    @field:Json(name = "height")
    val height: Int,
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "width")
    val width: Int
)