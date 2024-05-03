package com.application.tripapp.ui.main.images.imagepage

import com.application.tripapp.model.Picture

sealed class ImagePageState {
    data class PictureLoaded(val picture: Picture?): ImagePageState()

    class PictureError(val str: String) : ImagePageState()
}