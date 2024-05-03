package com.application.tripapp.ui.picture

import com.application.tripapp.db.PictureEntity

sealed class PictureAction {
    data object LoadPicture: PictureAction()
    data class AddPicture(val picture: PictureEntity?): PictureAction()
    data class DeletePicture(val picture: PictureEntity?): PictureAction()
}