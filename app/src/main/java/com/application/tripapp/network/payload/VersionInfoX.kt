package com.application.tripapp.network.payload

data class VersionInfoX(
    val deleted: Boolean,
    val documentKey: String,
    val version: Int
)