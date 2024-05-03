package com.application.tripapp.ui.asteroidpage

import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.Asteroid
import com.application.tripapp.ui.picture.PictureAction

sealed class AsteroidPageAction {
    data object LoadAsteroid: AsteroidPageAction()
    data class AddAsteroid(val asteroid: Asteroid): AsteroidPageAction()
    data class DeleteAsteroid(val asteroid: Asteroid): AsteroidPageAction()
}