package com.application.tripapp.usecase

import com.application.tripapp.model.Asteroid
import com.application.tripapp.repository.AsteroidRepository
import com.application.tripapp.utils.convertNearEarthObjectsToAsteroid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.round

class LoadAsteroidDataUseCase @Inject constructor(
    private val repository: AsteroidRepository
) {
    suspend fun getAsteroids(dateStart: String, dateEnd: String): Flow<List<Asteroid>> = flow {
        val response = repository.getAsteroids(dateStart, dateEnd)
        val asteroids = response.body()?.near_earth_objects?.values?.flatten()?.map { neo ->
            Asteroid(
                id = neo.id.toInt(),
                nasa_jpl_url = neo.nasa_jpl_url,
                name = neo.name,
                absolute_magnitude_h = round(neo.absolute_magnitude_h * 100) / 100,
                is_potentially_hazardous_asteroid = neo.is_potentially_hazardous_asteroid,
                estimated_diameter_max = round(neo.estimated_diameter.meters.estimated_diameter_max * 100) / 100,
                estimated_diameter_min = neo.estimated_diameter.meters.estimated_diameter_min,
                close_approach_date = neo.close_approach_data
            )
        }
        asteroids?.let { emit(it) }
    }

    suspend fun getAsteroidById(id: String): Flow<Asteroid> = flow {
        val response = repository.getAsteroidById(id)
        if (response.isSuccessful) {
            val nearEarthObjects = response.body()
            if (nearEarthObjects != null) {
                val asteroid = convertNearEarthObjectsToAsteroid(nearEarthObjects)
                emit(asteroid)
            } else {

            }
        } else {

        }
    }
}