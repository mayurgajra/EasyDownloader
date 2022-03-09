package com.mayurg.instadownloader_data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface InstaDownloaderApi {

    companion object {
        const val BASE_URL = "https://www.instagram.com/"
    }


    @GET
    suspend fun getMediaInfoFromUrl(
        @Url url: String,
        @Query("by") a: String
    ): Response<ResponseBody>


}