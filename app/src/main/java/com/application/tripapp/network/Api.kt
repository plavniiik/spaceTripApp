package com.application.tripapp.network

import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(): Response<PictureOfTheDayResponse>
}