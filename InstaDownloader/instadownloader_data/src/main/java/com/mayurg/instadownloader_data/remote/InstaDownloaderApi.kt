package com.mayurg.instadownloader_data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface InstaDownloaderApi {

    companion object {
        const val BASE_URL = "https://www.instagram.com/"

        val instance: InstaDownloaderApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://scontent-ham3-1.cdninstagram.com/v/t51.2885-15/")
                .build()
                .create(InstaDownloaderApi::class.java)
        }
    }


    @GET
    suspend fun getMediaInfoFromUrl(
        @Url url: String,
        @Query("url") a: String
    ): Response<ResponseBody>

    @GET
    suspend fun downloadInstaImage(
        @Url url: String,
    ): Response<ResponseBody>


}