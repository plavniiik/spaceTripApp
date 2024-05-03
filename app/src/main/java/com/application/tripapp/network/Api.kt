package com.application.tripapp.network

import com.application.tripapp.network.asteroid.AsteroidResponse
import com.application.tripapp.network.asteroid.NearEarthObjects
import retrofit2.http.Query
import com.application.tripapp.network.mars.MarsRoverImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(): Response<PictureOfTheDayResponse>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsRoverImages(@Query("earth_date") earthDate: String): Response<MarsRoverImagesResponse>

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
    ): Response<AsteroidResponse>

    @GET("neo/rest/v1/neo/{id}")
    suspend fun getAsteroidById(@Path("id") id: String): Response<NearEarthObjects>
}