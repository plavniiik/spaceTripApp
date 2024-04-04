package com.application.tripapp.ui.saved.pictures

import com.application.tripapp.db.PictureEntity

sealed class SavedPicturesState {
    class Error(val error: String) : SavedPicturesState()
    class PicturesLoaded(val pictures: List<PictureEntity>) : SavedPicturesState()
}