package com.application.tripapp.usecase

import android.util.Log
import com.application.tripapp.model.Payload
import com.application.tripapp.network.payload.Experiment
import com.application.tripapp.repository.PayloadExperimentRepository
import com.application.tripapp.utils.convertPayloadResponseToPayload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadPayloadExperimentUseCase @Inject constructor(
    private val repository: PayloadExperimentRepository
) {

    suspend fun getPayloadById(id: String): Flow<Payload?> = flow {
        val response = repository.getPayload(id)
        if (response.isSuccessful) {
            val payloadResponse = response.body()
            if (payloadResponse != null) {
                val payload = convertPayloadResponseToPayload(payloadResponse)
                emit(payload)
            } else {
                emit(null)
            }
        } else {
            emit(null)
        }
    }


}

