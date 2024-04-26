package com.application.tripapp.network.payload

data class CommonName(
    val annotationValue: String,
    val annotationValueLower: String,
    val branch: List<String>,
    val definition: String,
    val freeOntology: Boolean,
    val id: String,
    val mapping: List<Any>,
    val termAccession: String,
    val termSource: String
)