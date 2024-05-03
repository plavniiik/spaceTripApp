package com.application.tripapp.model

import com.application.tripapp.network.asteroid.CloseApproachData

data class Asteroid(
    val id: Int,
    val nasa_jpl_url: String,
    val name: String,
    val absolute_magnitude_h: Double,
    val is_potentially_hazardous_asteroid: Boolean,
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double,
    val close_approach_date: List<CloseApproachData>
)