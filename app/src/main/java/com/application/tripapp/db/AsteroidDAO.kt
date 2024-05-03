package com.application.tripapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.application.tripapp.model.Asteroid
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDAO {

    @Insert
    suspend fun addAsteroid(item: AsteroidEntity)

    @Query("DELETE FROM AsteroidEntity WHERE id == :asteroidId")
    suspend fun deleteAsteroid(asteroidId: Long)

    @Query("SELECT * FROM AsteroidEntity")
    suspend fun getAsteroids(): List<AsteroidEntity>

    @Query("SELECT * FROM AsteroidEntity WHERE userId == :userId")
    fun getUserAsteroids(userId: String): Flow<List<AsteroidEntity>>

    @Query("SELECT COUNT(*) > 0 FROM AsteroidEntity WHERE id == :asteroidId AND userId == :userId")
    fun hasAsteroid(asteroidId: String, userId: String): Flow<Boolean>
}