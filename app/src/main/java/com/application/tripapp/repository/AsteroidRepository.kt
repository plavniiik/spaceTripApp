package com.application.tripapp.repository

import com.application.tripapp.network.Api
import com.application.tripapp.network.asteroid.AsteroidResponse
import retrofit2.Response
import javax.inject.Inject

class AsteroidRepository @Inject constructor(private val api: Api) {
    suspend fun getAsteroids(startDate: String, endDate: String) = api.getAsteroids(startDate,endDate)
    suspend fun getAsteroidById(id: String) = api.getAsteroidById(id)
}