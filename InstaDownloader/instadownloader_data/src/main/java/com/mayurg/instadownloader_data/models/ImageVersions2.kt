package com.mayurg.instadownloader_data.models


import com.squareup.moshi.Json

data class ImageVersions2(
    @field:Json(name = "candidates")
    val candidates: List<Candidate>
)