package com.application.tripapp.ui.picture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.repository.PictureOfTheDayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val repository: PictureOfTheDayRepository,
    private val firebaseRepository: FireBaseRepository
) :
    ViewModel() {

    val state = MutableLiveData<PictureState>()
    val isPictureAdded = MutableLiveData<Boolean>()

    fun checkIfPictureIsAdded(picture: PictureEntity) {
        viewModelScope.launch {
            val added = firebaseRepository.isPictureAdded(picture)
            isPictureAdded.postValue(added)
        }
    }

    fun processAction(action: PictureAction) {
        when (action) {
            is PictureAction.LoadPicture -> {
                loadPicture()

            }

            is PictureAction.AddPicture -> {
                addPicture(action.picture)
            }

            is PictureAction.DeletePicture -> {
                deletePicture(action.picture)
            }

            else -> {

            }
        }
    }

    private fun loadPicture() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getPicture()
                if (response.isSuccessful) {
                    response.body()?.let {
                        val picture = PictureEntity(
                            firebaseRepository.getPictureId(it),
                            it.explanation,
                            it.title,
                            it.url
                        )
                        state.postValue(
                            picture?.let {
                                PictureState.PictureLoaded(picture)
                            }
                        )
                        checkIfPictureIsAdded(picture)
                    }
                }
            } catch (e: Exception) {
                state.postValue(PictureState.PictureError("Error: ${e.message}"))
            }
        }
    }

    private fun addPicture(picture: PictureEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firebaseRepository.savePictureOfTheDay(picture, {
                    isPictureAdded.postValue(true)
                }, {
                    state.postValue(PictureState.PictureError("Error: ${it}"))
                })
            } catch (e: Exception) {
                state.postValue(PictureState.PictureError("Error: ${e.message}"))
            }
        }
    }

    private fun deletePicture(picture: PictureEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firebaseRepository.deletePictureOfTheDay(picture.id, {
                    isPictureAdded.postValue(false)
                }, {
                    state.postValue(PictureState.PictureError("Error: ${it}"))
                })
            } catch (e: Exception) {
                state.postValue(PictureState.PictureError("Error: ${e.message}"))
            }
        }
    }

}