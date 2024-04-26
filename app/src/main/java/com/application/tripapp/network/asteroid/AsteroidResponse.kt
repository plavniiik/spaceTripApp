package com.application.tripapp.network.asteroid

data class AsteroidResponse(
    val links: Links,
    val element_count: Int,
    val near_earth_objects: Map<String, List<NearEarthObjects>>
)