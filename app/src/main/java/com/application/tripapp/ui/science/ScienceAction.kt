package com.application.tripapp.ui.science

import com.application.tripapp.ui.asteroids.AsteroidAction

sealed class ScienceAction {
    data object LoadData : ScienceAction()
}