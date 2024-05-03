package com.application.tripapp.ui.mars


sealed class MarsRoverAction {
    data object LoadPicture : MarsRoverAction()
    data object Loading : MarsRoverAction()
}