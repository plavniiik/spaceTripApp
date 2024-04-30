package com.application.tripapp.ui.asteroids

import com.application.tripapp.db.AsteroidEntity

sealed class AsteroidAction {
     data object LoadAsteroid : AsteroidAction()

     data class AddAsteroid(val asteroids: AsteroidEntity?): AsteroidAction()
     data class DeleteAsteroid(val asteroids: AsteroidEntity?): AsteroidAction()
}