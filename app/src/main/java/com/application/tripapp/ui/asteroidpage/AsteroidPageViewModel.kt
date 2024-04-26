package com.application.tripapp.ui.asteroidpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.usecase.LoadAsteroidDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsteroidPageViewModel @Inject constructor(
    private val useCase: LoadAsteroidDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AsteroidPageState>(AsteroidPageState.AsteroidLoaded(null))
    val state: StateFlow<AsteroidPageState> get() = _state

    fun processAction(action: AsteroidPageAction, id:String) {
        when (action) {
            is AsteroidPageAction.LoadAsteroid -> {
                loadAsteroid(id)
            }
        }
    }

    private fun loadAsteroid(id:String) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    useCase.getAsteroidById(id).collect { asteroid ->
                        _state.value = AsteroidPageState.AsteroidLoaded(asteroid)
                    }
                } catch (e: Exception) {
                    _state.value= AsteroidPageState.AsteroidError("Error: ${e.message}")
                }
            }
        }
}