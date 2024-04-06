package com.application.tripapp.ui.saved.pictures

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedPictureViewModel @Inject constructor(private val repository: FireBaseRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(SavedPicturesState.PicturesLoaded(emptyList()))
    val state: StateFlow<SavedPicturesState> = _state

    fun processAction(action: SavedPicturesAction) {
        when (action) {
            is SavedPicturesAction.Load -> {
                loadPictures()
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
}