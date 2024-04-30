package com.application.tripapp.ui.saved.asteroids

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.repository.AsteroidsEntityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.logging.Level.INFO
import javax.inject.Inject

@HiltViewModel
class SavedAsteroidsViewModel @Inject constructor(private val repository: AsteroidsEntityRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(SavedAsteroidsState.AsteroidsLoaded(emptyList()))
    val state: StateFlow<SavedAsteroidsState> = _state

    fun processAction(action: SavedAsteroidsAction, id: Long) {
        when (action) {
            is SavedAsteroidsAction.Load -> {
                loadAsteroids()
            }
            is SavedAsteroidsAction.DeleteAsteroid -> {
                deleteAsteroid(id)
            }
        }
    }

    private fun loadAsteroids() {
        Log.println(Log.INFO,"list", "list")
        viewModelScope.launch {
            repository.getAsteroidList(viewModelScope)
            repository.listAsteroids.collect { asteroids ->
                _state.value = SavedAsteroidsState.AsteroidsLoaded(asteroids)
            }
        }
    }


    private fun deleteAsteroid(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteAsteroid(id)
            } catch (e: Exception) {
               /* _state.value = AsteroidPageState.AsteroidError("Error: ${e.message}")*/
            }
        }
    }
}