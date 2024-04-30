package com.application.tripapp.repository

import android.util.Log
import com.application.tripapp.db.AsteroidDAO
import com.application.tripapp.db.AsteroidEntity
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.Asteroid
import com.application.tripapp.network.PictureOfTheDayResponse
import com.application.tripapp.utils.toEntity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class AsteroidsEntityRepository @Inject constructor(
    private val asteroidDAO: AsteroidDAO,
    private val firebaseAuth: FirebaseAuth
) {
    val listAsteroids = MutableStateFlow<List<AsteroidEntity>>(emptyList())
    private val _hasAsteroid = MutableStateFlow<Boolean?>(null)
    val hasAsteroid: StateFlow<Boolean?> get() = _hasAsteroid
    fun getAsteroidList( coroutineScope: CoroutineScope) {
        val userId = firebaseAuth.currentUser?.uid
        coroutineScope.launch {
            userId?.let {
                asteroidDAO.getUserAsteroids(it).collect { asteroids ->
                    listAsteroids.emit(asteroids)
                }
            }
        }
    }

    suspend fun saveAsteroid(asteroid: Asteroid) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val asteroidEntity = AsteroidEntity(
                id = asteroid.id.toLong(),
                nasa_jpl_url = asteroid.nasa_jpl_url,
                name = asteroid.name,
                absolute_magnitude_h = asteroid.absolute_magnitude_h,
                is_potentially_hazardous_asteroid = asteroid.is_potentially_hazardous_asteroid,
                estimated_diameter_max = asteroid.estimated_diameter_max,
                userId = userId
            )
            asteroidDAO.addAsteroid(asteroidEntity)
        } else {
            Log.e("FireBaseRepository", "User is not logged in")
        }
    }

    suspend fun deleteAsteroid(asteroidId: Long) {
        asteroidDAO?.deleteAsteroid(asteroidId)
    }


    suspend fun checkIfAsteroidExists(asteroidId: String): Flow<Boolean> = flow {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            asteroidDAO.hasAsteroid(asteroidId, userId).collect { hasAsteroid ->
                emit(hasAsteroid)
            }
        } else {
            emit(false)
        }
    }
    fun hasAsteroid(asteroidId: String, coroutineScope: CoroutineScope): Flow<Boolean> = flow {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            coroutineScope.launch {
                asteroidDAO.hasAsteroid(asteroidId, userId).collect { hasAsteroid ->
                    emit(hasAsteroid)
                }
            }
        } else {
            emit(false)
        }
    }

}