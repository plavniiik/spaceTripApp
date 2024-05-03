package com.application.tripapp.repository

import com.application.tripapp.network.Api
import javax.inject.Inject

class MarsRoverRepository @Inject constructor(private val api: Api) {

    suspend fun getRoverImages(earthDate: String) = api.getMarsRoverImages(earthDate)
}