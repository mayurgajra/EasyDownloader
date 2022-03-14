package com.mayurg.easydownloader.utils

import org.json.JSONException
import org.json.JSONObject

object InstaParser {

    fun getDownloadUrl(response: String): String {
        try {
            val rootObj = JSONObject(response)
            val type = rootObj.getJSONObject("data").getString("type")

            when (type) {
                "image" -> {
                    return getImageUrl(rootObj)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ""
    }

    private fun getImageUrl(rootObj: JSONObject): String {
        try {
            val images = rootObj.getJSONObject("data").getJSONObject("images")
            val original = images.getJSONObject("original")

            if (original.has("high")) {
                return original.getString("high")
            }

            if (original.has("standard")) {
                return original.getString("standard")
            }

            if (original.has("low")) {
                return original.getString("standard")
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ""
    }
}