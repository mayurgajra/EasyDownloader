package com.halilibo.composevideoplayer

import android.net.Uri
import androidx.annotation.RawRes

sealed class VideoPlayerSource {
    data class Raw(@RawRes val resId: Int) : VideoPlayerSource()
    data class Network(val url: String, val headers: Map<String, String> = mapOf()) : VideoPlayerSource()
    data class LocalStorage(val uri: Uri) : VideoPlayerSource()
}