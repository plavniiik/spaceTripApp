package com.application.tripapp.network.payload

data class Person(
    val emailAddress: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val middleName: String,
    val phone: String,
    val versionInfo: VersionInfoX
)