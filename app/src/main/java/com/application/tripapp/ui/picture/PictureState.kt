package com.application.tripapp.ui.picture

import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.PictureOfTheDay

sealed class PictureState {
    class PictureLoaded(val picture: PictureEntity): PictureState()
    class PictureError(val str: String) : PictureState()
    object PictureAdded : PictureState()
    object PictureDeleted : PictureState()}