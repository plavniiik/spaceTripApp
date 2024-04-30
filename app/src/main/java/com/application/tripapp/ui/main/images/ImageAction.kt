package com.application.tripapp.ui.main.images

sealed class ImageAction {
    class LoadPictures(val keyWord: String?) : ImageAction()
}