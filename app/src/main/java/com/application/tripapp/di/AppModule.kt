package com.application.tripapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.application.tripapp.db.AppDataBase
import com.application.tripapp.db.AsteroidDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "dataBase")
            .build()
    }

    @Singleton
    @Provides
    fun provideItemDao(appDataBase: AppDataBase): AsteroidDAO {
        return appDataBase.getAsteroidDao()
    }


}
