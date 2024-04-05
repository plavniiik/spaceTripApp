package com.application.tripapp.ui.saved.pictures

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.room.InvalidationTracker
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SavedPictureViewModel @Inject constructor(private val repository: FireBaseRepository) :
    ViewModel() {

    val state = MutableLiveData<SavedPicturesState>()

    private val picturesObserver =
        androidx.lifecycle.Observer<List<PictureEntity>> { pictures: List<PictureEntity> ->
        }

    init {
        repository.picturesResult.observeForever(picturesObserver)
    }

    fun processAction(action: SavedPicturesAction) {
        when (action) {
            is SavedPicturesAction.Load -> {
                loadPictures()
            }
        }
    }

    private fun loadPictures() {
        repository.getPicturesOfTheDay(
            onSuccess = { results ->
                val resultEntities = results.map {
                    PictureEntity(
                        it.id,
                        it.explanation,
                        it.title,
                        it.url
                    )
                }
                state.postValue(SavedPicturesState.PicturesLoaded(resultEntities))
            },
            onError = { error ->
                state.postValue(SavedPicturesState.Error(error))
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        repository.picturesResult.removeObserver(picturesObserver)
    }
}