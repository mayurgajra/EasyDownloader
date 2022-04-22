package com.mayurg.instadownloader_data.repository

import org.json.JSONException
import org.json.JSONObject

class InstaParser {

    companion object {
        private const val KEY_DATA = "data"
        private const val KEY_TYPE = "type"
        private const val KEY_IMAGES = "images"
        private const val KEY_VIDEOS = "videos"
        private const val KEY_ORIGINAL = "original"
        private const val KEY_SIDECAR = "sidecar"

        // post types
        private const val KEY_TYPE_IMAGE = "image"
        private const val KEY_TYPE_SIDECAR = "sidecar"
        private const val KEY_TYPE_VIDEO = "video"

        //video quality
        private const val KEY_QUALITY_HIGH = "high"
        private const val KEY_QUALITY_STANDARD = "standard"
        private const val KEY_QUALITY_LOW = "low"

    }

    fun getType(response: String): String {
        try {
            val rootObj = JSONObject(response)
            return rootObj.getJSONObject(KEY_DATA).optString(KEY_TYPE, "")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ""
    }

    fun getDownloadUrl(response: String): List<String> {
        try {
            val rootObj = JSONObject(response)

            when (getType(response)) {
                KEY_TYPE_IMAGE -> {
                    return listOf(getImageUrl(rootObj))
                }

                KEY_TYPE_SIDECAR -> {
                    return getSideCarUrls(rootObj)
                }

                KEY_TYPE_VIDEO -> {
                    return listOf(getVideoUrl(rootObj))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return emptyList()
    }

    private fun getImageUrl(rootObj: JSONObject): String {
        try {
            val images = rootObj.getJSONObject(KEY_DATA).getJSONObject(KEY_IMAGES)
            val original = images.getJSONObject(KEY_ORIGINAL)

            return getBestQualityUrl(original)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ""
    }

    private fun getSideCarUrls(rootObj: JSONObject): List<String> {
        val urls = mutableListOf<String>()
        try {
            val sidecar = rootObj.getJSONObject(KEY_DATA).getJSONArray(KEY_SIDECAR)

            for (i in 0 until sidecar.length()) {
                val images = sidecar.getJSONObject(i).getJSONObject(KEY_IMAGES)
                val original = images.getJSONObject(KEY_ORIGINAL)

                urls.add(getBestQualityUrl(original))
            }


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return urls
    }


    private fun getVideoUrl(rootObj: JSONObject): String {
        try {
            val videos = rootObj.getJSONObject(KEY_DATA).getJSONObject(KEY_VIDEOS)

            return getBestQualityUrl(videos)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ""
    }

    private fun getBestQualityUrl(jsonObject: JSONObject): String {
        if (jsonObject.has(KEY_QUALITY_HIGH)) {
            return jsonObject.getString(KEY_QUALITY_HIGH)
        }

        if (jsonObject.has(KEY_QUALITY_STANDARD)) {
            return jsonObject.getString(KEY_QUALITY_STANDARD)
        }

        if (jsonObject.has(KEY_QUALITY_LOW)) {
            return jsonObject.getString(KEY_QUALITY_LOW)
        }

        return ""
    }

}