package com.application.tripapp.ui.saved.pictures

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.ui.picture.PictureState
import com.application.tripapp.ui.saved.asteroids.SavedAsteroidsAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedPictureViewModel @Inject constructor(
    private val repository: FireBaseRepository,
    private val firebaseRepository: FireBaseRepository
) :
    ViewModel() {
    private val _state = MutableStateFlow(SavedPicturesState.PicturesLoaded(emptyList()))
    val state: StateFlow<SavedPicturesState> = _state

    fun processAction(action: SavedPicturesAction, id: String) {
        when (action) {
            is SavedPicturesAction.Load -> {
                loadPictures()
            }

            is SavedPicturesAction.DeletePictures -> {
                deletePictures(id)
            }
        }
    }

    private fun loadPictures() {
        viewModelScope.launch {
            repository.pictures.collect() { pictures ->
                Log.d("SavedPictureViewModel", "Received pictures: ${pictures.size}")
                val pictureEntities = pictures.map {
                    PictureEntity(
                        it.id,
                        it.explanation,
                        it.title,
                        it.url
                    )
                }
                _state.value = SavedPicturesState.PicturesLoaded(pictureEntities)
            }
        }
        repository.getPicturesOfTheDay(viewModelScope)
    }

    private fun deletePictures(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firebaseRepository.deletePictureOfTheDay(id, {

                }, {
                    /*_state.value = SavedPicturesState.Error("Error: $it")*/
                })
            } catch (e: Exception) {
                /*_state.value = SavedPicturesState.Error("Error: ${e.message}")*/
            }
        }
    }

}