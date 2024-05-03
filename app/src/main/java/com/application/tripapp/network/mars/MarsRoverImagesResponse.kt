package com.application.tripapp.network.mars

data class MarsRoverImagesResponse(
    val photos: List<Photo>
)

data class Photo(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)

data class CameraX(
    val full_name: String,
    val name: String
)

data class Camera(
    val full_name: String,
    val id: Int,
    val name: String,
    val rover_id: Int
)

data class Rover(
    val cameras: List<CameraX>,
    val id: Int,
    val landing_date: String,
    val launch_date: String,
    val max_date: String,
    val max_sol: Int,
    val name: String,
    val status: String,
    val total_photos: Int
)