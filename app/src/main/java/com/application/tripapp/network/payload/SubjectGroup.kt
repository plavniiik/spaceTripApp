package com.application.tripapp.network.payload

data class SubjectGroup(
    val commonName: CommonName,
    val description: String,
    val identifier: String,
    val link: String,
    val scientificName: ScientificName
)