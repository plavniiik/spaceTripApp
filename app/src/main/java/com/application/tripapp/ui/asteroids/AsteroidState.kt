package com.application.tripapp.ui.asteroids

import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.Asteroid
import com.application.tripapp.ui.picture.PictureState

sealed class AsteroidState {
    class AsteroidLoaded(val asteroids: List<Asteroid>?): AsteroidState()
    class AsteroidsError(val str: String) : AsteroidState()
}