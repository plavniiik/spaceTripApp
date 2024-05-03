package com.application.tripapp.ui.asteroids

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.ui.mars.MarsRoverAction
import com.application.tripapp.ui.mars.MarsRoverState
import com.application.tripapp.ui.picture.PictureState
import com.application.tripapp.usecase.LoadAsteroidDataUseCase
import com.application.tripapp.usecase.LoadPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsteroidViewModel @Inject constructor(
    private val useCase: LoadAsteroidDataUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AsteroidState>(AsteroidState.AsteroidLoaded(null))
    val state: StateFlow<AsteroidState> get() = _state

    fun processAction(action: AsteroidAction, dateStart: String, dateEnd: String) {
        when (action) {
            is AsteroidAction.LoadAsteroid -> loadAsteroids(dateStart,dateEnd)
            else -> {}
        }
    }

    fun loadAsteroids(dateStart: String, dateEnd: String) {
        viewModelScope.launch {
            try {
                useCase.getAsteroids(dateStart,dateEnd).collect { asteroids ->
                    _state.value = AsteroidState.AsteroidLoaded(asteroids)
                }

            } catch (e: Exception) {
                _state.value = AsteroidState.AsteroidsError("Error: ${e.message}")
            }
        }
    }
}