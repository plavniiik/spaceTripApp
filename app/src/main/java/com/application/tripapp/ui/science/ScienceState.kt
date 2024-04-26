package com.application.tripapp.ui.science

import com.application.tripapp.model.Asteroid
import com.application.tripapp.model.Payload
import com.application.tripapp.network.payload.Experiment
import com.application.tripapp.ui.asteroids.AsteroidState

sealed class ScienceState {

    class PayloadLoaded(val payload: Payload?) : ScienceState()
    class ScienceError(val str: String) : ScienceState()
    data object ScienceLoad : ScienceState()
}