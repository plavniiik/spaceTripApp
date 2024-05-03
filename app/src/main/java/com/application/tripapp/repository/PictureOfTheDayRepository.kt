package com.application.tripapp.repository

import com.application.tripapp.network.Api
import com.application.tripapp.network.PictureOfTheDayResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PictureOfTheDayRepository @Inject constructor(private val api: Api) {
    suspend fun getPicture() = api.getPictureOfTheDay()


}