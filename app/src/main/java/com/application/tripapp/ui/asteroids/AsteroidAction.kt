package com.application.tripapp.ui.asteroids

import com.application.tripapp.ui.main.MainAction

sealed class AsteroidAction {
     data object LoadAsteroid : AsteroidAction()
}