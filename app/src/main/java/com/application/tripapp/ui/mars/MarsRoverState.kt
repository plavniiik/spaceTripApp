package com.application.tripapp.ui.mars

import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.MarsImage
import com.application.tripapp.ui.picture.PictureState

sealed class MarsRoverState {

    class PicturesLoaded(val pictures: List<MarsImage>): MarsRoverState()
    class PicturesError(val str: String) : MarsRoverState()
    data object Loading : MarsRoverState()

}