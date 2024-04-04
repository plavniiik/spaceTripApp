package com.application.tripapp.ui.main

import android.graphics.Picture
import com.application.tripapp.model.PictureOfTheDay

sealed class MainState {

    class PictureLoaded(val picture: PictureOfTheDay): MainState()
    class PictureError(val str: String) : MainState()

}