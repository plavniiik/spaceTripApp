package com.application.tripapp.ui.asteroidpage

import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.Asteroid
import com.application.tripapp.ui.picture.PictureState

sealed class AsteroidPageState {

    class AsteroidLoaded(val asteroid: Asteroid?): AsteroidPageState()
    class AsteroidError(val str: String) : AsteroidPageState()
}