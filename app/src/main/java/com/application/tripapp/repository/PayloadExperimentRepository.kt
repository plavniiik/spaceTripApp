package com.application.tripapp.repository

import com.application.tripapp.network.Api
import com.application.tripapp.network.ApiRepository
import javax.inject.Inject

class PayloadExperimentRepository @Inject constructor(private val api: ApiRepository) {
    suspend fun getPayload(id: String) = api.getPayloadById(id)
}