package com.application.tripapp.ui.saved.pictures

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedPictureViewModel @Inject constructor(private val repository: FireBaseRepository) :
    ViewModel() {

    val state = MutableLiveData<SavedPicturesState>()

    fun processAction(action: SavedPicturesAction) {
        when (action) {
            is SavedPicturesAction.Load -> {
                loadPictures()
            }
        }
    }

    private fun loadPictures() {
        repository.getPicturesOfTheDay(
            onSuccess = { pictures ->
                val pictureEntities = pictures.map {
                    PictureEntity(
                        it.id,
                        it.explanation,
                        it.title,
                        it.url
                    )
                }
                state.postValue(SavedPicturesState.PicturesLoaded(pictureEntities))
            },
            onError = { error ->
                state.postValue(SavedPicturesState.Error(error))
            }
        )
    }
}
