package com.application.tripapp.repository

import com.application.tripapp.network.Api
import javax.inject.Inject

class PictureOfTheDayRepository @Inject constructor(private val api: Api) {
    suspend fun getPicture() = api.getPictureOfTheDay()
}