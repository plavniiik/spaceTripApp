package com.application.tripapp.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
private const val SHARED_PREF = "sharedPref"
private const val IS_FIRST_LAUNCH = "is_first_launch"

@Singleton
class SharedPreferencesRepository @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences


    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    fun isFirstLaunch(): Boolean {
        return sharedPreferences?.getBoolean(IS_FIRST_LAUNCH, true) ?: true
    }

    fun setIsFirstLaunch() {
        sharedPreferences?.edit {
            putBoolean(IS_FIRST_LAUNCH, false)
        }
    }
}