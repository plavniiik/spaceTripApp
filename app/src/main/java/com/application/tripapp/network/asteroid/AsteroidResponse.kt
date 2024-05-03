package com.application.tripapp.network.asteroid

data class AsteroidResponse(
    val links: Links,
    val element_count: Int,
    val near_earth_objects: Map<String, List<NearEarthObjects>>
)

data class CloseApproachData(
    val close_approach_date: String,
    val close_approach_date_full: String,
    val epoch_date_close_approach: Long,
    val miss_distance: MissDistance,
    val orbiting_body: String,
    val relative_velocity: RelativeVelocity
)

data class Diameter(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
)

data class EstimatedDiameter(
    val kilometers: Diameter,
    val meters: Diameter,
    val miles: Diameter,
    val feet: Diameter
)

data class Link(
    val self: String
)

data class Links(
    val next: String,
    val prev: String,
    val self: String
)

data class MissDistance(
    val astronomical: String,
    val kilometers: String,
    val lunar: String,
    val miles: String
)

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

data class RelativeVelocity(
    val kilometers_per_hour: String,
    val kilometers_per_second: String,
    val miles_per_hour: String
)