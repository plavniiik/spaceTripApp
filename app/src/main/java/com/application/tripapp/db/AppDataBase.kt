package com.application.tripapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AsteroidEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getAsteroidDao(): AsteroidDAO

}