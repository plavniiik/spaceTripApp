package com.application.tripapp.ui.main

import com.application.tripapp.model.PictureOfTheDay

sealed class MainState {

    data class PictureLoaded(val picture: PictureOfTheDay?): MainState() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as PictureLoaded

            if (picture != other.picture) return false

            return true
        }

        override fun hashCode(): Int {
            return picture?.hashCode() ?: 0
        }
    }
    class PictureError(val str: String) : MainState()

}