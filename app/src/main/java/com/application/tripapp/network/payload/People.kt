package com.application.tripapp.network.payload

data class People(
    val institution: String,
    val person: Person,
    val roles: List<String>
)