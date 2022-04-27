package com.mayurg.fbdownloader_data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url

interface FbDownloaderApi {

    companion object {
        const val BASE_URL = "https://facebook17.p.rapidapi.com/api/facebook/links/"

        val instance: FbDownloaderApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build()
                .create(FbDownloaderApi::class.java)
        }
    }


    @GET
    suspend fun getMediaInfoFromUrl(
        @Url url: String,
    ): Response<ResponseBody>

    @GET
    suspend fun downloadFbMedia(
        @Url url: String,
    ): Response<ResponseBody>


}