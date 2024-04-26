package com.application.tripapp.network.asteroid

data class NearEarthObjects(
    val links: Links,
    val id: String,
    val neo_reference_id: String,
    val name: String,
    val nasa_jpl_url: String,
    val absolute_magnitude_h: Double,
    val estimated_diameter: EstimatedDiameter,
    val is_potentially_hazardous_asteroid: Boolean,
    val close_approach_data: List<CloseApproachData>,
    val is_sentry_object: Boolean
)