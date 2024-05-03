package com.application.tripapp.network

import com.application.tripapp.network.payload.PayloadResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRepository {
    @GET("payload/{id}")
    suspend fun getPayloadById(@Path("id") id: String): Response<PayloadResponse>


}