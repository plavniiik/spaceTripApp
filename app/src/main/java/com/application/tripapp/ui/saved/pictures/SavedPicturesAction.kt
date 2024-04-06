package com.application.tripapp.ui.saved.pictures

sealed class SavedPicturesAction {
    data object Load : SavedPicturesAction()
}