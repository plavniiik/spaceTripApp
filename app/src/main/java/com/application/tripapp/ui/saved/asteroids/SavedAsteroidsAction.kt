package com.application.tripapp.ui.saved.asteroids

import com.application.tripapp.db.AsteroidEntity
import com.application.tripapp.ui.asteroids.AsteroidAction
import com.application.tripapp.ui.saved.pictures.SavedPicturesAction

sealed class SavedAsteroidsAction {
    data object Load : SavedAsteroidsAction()
    data object DeleteAsteroid: SavedAsteroidsAction()

}