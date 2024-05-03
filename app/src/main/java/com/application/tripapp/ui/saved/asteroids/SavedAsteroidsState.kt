package com.application.tripapp.ui.saved.asteroids

import com.application.tripapp.db.AsteroidEntity
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.ui.saved.pictures.SavedPicturesState

sealed class SavedAsteroidsState {
    class Error(val error: String) : SavedAsteroidsState()
    class AsteroidsLoaded(val asteroids: List<AsteroidEntity>) : SavedAsteroidsState()
}