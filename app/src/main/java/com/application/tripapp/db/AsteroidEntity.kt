package com.application.tripapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.tripapp.network.asteroid.CloseApproachData

@Entity(tableName = "AsteroidEntity")
data class AsteroidEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo("nasa_jpl_url")
    val nasa_jpl_url: String,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("absolute_magnitude_h")
    val absolute_magnitude_h: Double,
    @ColumnInfo("is_potentially_hazardous_asteroid")
    val is_potentially_hazardous_asteroid: Boolean,
    @ColumnInfo("estimated_diameter_max")
    val estimated_diameter_max: Double,
    @ColumnInfo("userId")
    val userId: String
)