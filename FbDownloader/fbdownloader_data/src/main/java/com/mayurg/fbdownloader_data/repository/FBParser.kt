package com.mayurg.fbdownloader_data.repository

import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class FBParser @Inject constructor() {
    fun getDownloadUrl(body: String): List<String> {
        val urls = mutableListOf<String>()
        try {
            val rootJson = JSONObject(body)
            val urlArray = rootJson.getJSONArray("url")
            for (i in 0 until urlArray.length()) {
                val urlObj = urlArray.getJSONObject(i)
                if (urlObj.optString("subname", "").equals("HD")) {
                    if (urlObj.optString("url", "").isNotEmpty()) {
                        urls.add(urlObj.optString("url", ""))
                        break
                    }
                }

                if (urlObj.optString("subname", "").equals("SD")) {
                    if (urlObj.optString("url", "").isNotEmpty()) {
                        urls.add(urlObj.optString("url", ""))
                        break
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return urls

    }


}