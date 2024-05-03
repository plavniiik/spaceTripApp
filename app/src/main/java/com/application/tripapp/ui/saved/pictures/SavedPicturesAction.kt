package com.application.tripapp.ui.saved.pictures

import com.application.tripapp.ui.saved.asteroids.SavedAsteroidsAction

sealed class SavedPicturesAction {
    data object Load : SavedPicturesAction()
    data object DeletePictures: SavedPicturesAction()
}