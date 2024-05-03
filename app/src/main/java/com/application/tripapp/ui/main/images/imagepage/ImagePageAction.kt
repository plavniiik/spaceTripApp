package com.application.tripapp.ui.main.images.imagepage

sealed class ImagePageAction  {
    class LoadPictures(val keyWord: String?) : ImagePageAction()
}