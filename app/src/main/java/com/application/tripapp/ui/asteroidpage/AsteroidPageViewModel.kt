package com.application.tripapp.ui.asteroidpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.db.AsteroidEntity
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.Asteroid
import com.application.tripapp.repository.AsteroidsEntityRepository
import com.application.tripapp.ui.picture.PictureAction
import com.application.tripapp.ui.picture.PictureState
import com.application.tripapp.usecase.LoadAsteroidDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsteroidPageViewModel @Inject constructor(
    private val useCase: LoadAsteroidDataUseCase,
    private val repository: AsteroidsEntityRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AsteroidPageState>(AsteroidPageState.AsteroidLoaded(null))
    val state: StateFlow<AsteroidPageState> get() = _state
    val isAsteroidAdded = MutableStateFlow<Boolean>(false)


    fun checkIfAsteroidIsAdded(id: String) {
        viewModelScope.launch {
            repository.checkIfAsteroidExists(id).collect { hasAsteroid ->
                isAsteroidAdded.value = hasAsteroid
            }
        }
    }

    fun processAction(action: AsteroidPageAction, id: String) {
        when (action) {
            is AsteroidPageAction.LoadAsteroid -> {
                loadAsteroid(id)
            }

            is AsteroidPageAction.AddAsteroid -> addAsteroid(action.asteroid)
            is AsteroidPageAction.DeleteAsteroid -> deleteAsteroid(action.asteroid)
        }
    }

    private fun loadAsteroid(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCase.getAsteroidById(id).collect { asteroid ->
                    _state.value = AsteroidPageState.AsteroidLoaded(asteroid)
                    checkIfAsteroidIsAdded(id)
                }
            } catch (e: Exception) {
                _state.value = AsteroidPageState.AsteroidError("Error: ${e.message}")
            }
        }
    }

    private fun addAsteroid(asteroid: Asteroid) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.saveAsteroid(asteroid)
                isAsteroidAdded.value = true

            } catch (e: Exception) {
                _state.value = AsteroidPageState.AsteroidError("Error: ${e.message}")
            }
        }
    }

    private fun deleteAsteroid(asteroid: Asteroid) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteAsteroid(asteroid.id.toLong())
                isAsteroidAdded.value = false
            } catch (e: Exception) {
                _state.value = AsteroidPageState.AsteroidError("Error: ${e.message}")
            }
        }
    }
}
