package com.application.tripapp.ui.asteroidpage

import com.application.tripapp.ui.picture.PictureAction

sealed class AsteroidPageAction {
    data object LoadAsteroid: AsteroidPageAction()
}