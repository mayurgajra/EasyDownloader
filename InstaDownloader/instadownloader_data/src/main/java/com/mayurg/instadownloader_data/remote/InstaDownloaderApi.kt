package com.mayurg.instadownloader_data.remote

import com.mayurg.instadownloader_data.models.GetMediaInfo
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
        @Query("url") a: String
    ): Response<GetMediaInfo>


}