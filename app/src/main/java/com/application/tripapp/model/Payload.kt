package com.application.tripapp.model

import com.application.tripapp.network.payload.People
import com.application.tripapp.network.payload.VersionInfoX

data class Payload(
    val id: String,
    val description: String,
    val files: List<Any>,
    val identifier: String,
    val identifierLowercase: String,
    val payloadName: String,
    val people: List<People>,
    val type: String,
)

