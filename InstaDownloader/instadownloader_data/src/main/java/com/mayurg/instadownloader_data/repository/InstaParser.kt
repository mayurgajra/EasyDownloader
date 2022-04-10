package com.mayurg.instadownloader_data.repository

import org.json.JSONException
import org.json.JSONObject

class InstaParser {

    fun getDownloadUrl(response: String): List<String> {
        try {
            val rootObj = JSONObject(response)
            val type = rootObj.getJSONObject("data").getString("type")

            when (type) {
                "image" -> {
                    return listOf(getImageUrl(rootObj))
                }

                "sidecar" -> {
                    return getSideCarUrls(rootObj)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return emptyList()
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

    private fun getSideCarUrls(rootObj: JSONObject): List<String> {
        val imageUrls = mutableListOf<String>()
        try {
            val sidecar = rootObj.getJSONObject("data").getJSONArray("sidecar")

            for (i in 0 until sidecar.length()) {
                val images = sidecar.getJSONObject(i).getJSONObject("images")
                val original = images.getJSONObject("original")

                if (original.has("high")) {
                    imageUrls.add(original.getString("high"))
                    continue
                }

                if (original.has("standard")) {
                    imageUrls.add(original.getString("standard"))
                    continue
                }

                if (original.has("low")) {
                    imageUrls.add(original.getString("standard"))
                    continue
                }
            }


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return imageUrls
    }
}