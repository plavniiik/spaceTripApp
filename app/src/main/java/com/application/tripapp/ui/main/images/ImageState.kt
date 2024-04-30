package com.application.tripapp.ui.main.images

import androidx.paging.PagingData
import com.application.tripapp.model.Picture
import com.application.tripapp.model.PictureOfTheDay
import com.application.tripapp.ui.main.MainState

sealed class ImageState {
    data class PicturesLoaded(val pictures: PagingData<Picture>): ImageState()

    class PicturesError(val str: String) : ImageState()
}