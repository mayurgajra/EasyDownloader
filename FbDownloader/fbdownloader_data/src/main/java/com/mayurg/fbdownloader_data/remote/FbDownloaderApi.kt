package com.mayurg.fbdownloader_data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface FbDownloaderApi {

    companion object {
        const val BASE_URL = "https://facebook17.p.rapidapi.com/api/facebook/"

        val instance: FbDownloaderApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build()
                .create(FbDownloaderApi::class.java)
        }
    }


    @POST("links/")
    suspend fun getMediaInfoFromUrl(
        @Body fbMediaRequestBody: FbMediaRequestBody,
    ): Response<ResponseBody>

    @GET
    suspend fun downloadFbMedia(
        @Url url: String,
    ): Response<ResponseBody>


}