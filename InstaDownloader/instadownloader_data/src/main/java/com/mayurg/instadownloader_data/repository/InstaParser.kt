package com.mayurg.instadownloader_data.repository

import org.json.JSONException
import org.json.JSONObject

class InstaParser {

    companion object {
        private const val KEY_TYPE = "Type"
        private const val KEY_MEDIA = "media"


        // post types
        private const val KEY_TYPE_IMAGE = "Post-Image"
        private const val KEY_TYPE_CAROUSEL = "Carousel"
        private const val KEY_TYPE_VIDEO = "Post-Video"

    }

    fun getType(response: String): String {
        try {
            val rootObj = JSONObject(response)
            return rootObj.optString(KEY_TYPE, "")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ""
    }

    fun getDownloadUrl(response: String): List<String> {
        try {
            val rootObj = JSONObject(response)

            when (getType(response)) {
                KEY_TYPE_IMAGE, KEY_TYPE_VIDEO -> {
                    return listOf(getImageVideoUrl(rootObj))
                }

                KEY_TYPE_CAROUSEL -> {
                    return getSideCarUrls(rootObj)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return emptyList()
    }

    private fun getImageVideoUrl(rootObj: JSONObject): String {
        try {
            return rootObj.optString(KEY_MEDIA, "")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ""
    }

    private fun getSideCarUrls(rootObj: JSONObject): List<String> {
        val urls = mutableListOf<String>()
        try {
            val sidecar = rootObj.getJSONArray(KEY_MEDIA)

            for (i in 0 until sidecar.length()) {
                val url = sidecar.optString(i)
                urls.add(url)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return urls
    }

}