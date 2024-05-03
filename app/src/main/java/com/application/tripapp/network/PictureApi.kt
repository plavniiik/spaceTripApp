package com.application.tripapp.network

import com.application.tripapp.model.Picture
import com.application.tripapp.network.picture.PictureResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureApi {
    @GET("search")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Response<PictureResponse>

    @GET("search")
    suspend fun getImage(
        @Query("q") query: String,
    ): Response<PictureResponse>
}