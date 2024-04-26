package com.application.tripapp.ui.picture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.usecase.LoadPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val useCase: LoadPictureUseCase,
    private val firebaseRepository: FireBaseRepository
) : ViewModel() {

    private val _state = MutableStateFlow<PictureState>(PictureState.PictureLoaded(null))
    val state: StateFlow<PictureState> get() = _state

    val isPictureAdded = MutableStateFlow<Boolean>(false)


    fun checkIfPictureIsAdded(picture: PictureEntity) {
        viewModelScope.launch {
            firebaseRepository.isPictureAdded(picture).collect { result ->
                isPictureAdded.value = result
            }
        }
    }

    fun processAction(action: PictureAction) {
        when (action) {
            is PictureAction.LoadPicture -> loadPicture()
            is PictureAction.AddPicture -> addPicture(action.picture)
            is PictureAction.DeletePicture -> deletePicture(action.picture)
        }
    }

    private fun loadPicture() {
        if(_state.value is PictureState.PictureLoaded && (_state.value as PictureState.PictureLoaded).picture == null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    useCase.getPicture().collect { picture ->
                        _state.value = PictureState.PictureLoaded(picture)
                        checkIfPictureIsAdded(picture)
                    }
                } catch (e: Exception) {
                    _state.value = PictureState.PictureError("Error: ${e.message}")
                }
            }
        }
    }

    private fun addPicture(picture: PictureEntity?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firebaseRepository.savePictureOfTheDay(picture, {
                    isPictureAdded.value = true
                }) {
                    _state.value = PictureState.PictureError("Error: $it")
                }
            } catch (e: Exception) {
                _state.value = PictureState.PictureError("Error: ${e.message}")
            }
        }
    }

    private fun deletePicture(picture: PictureEntity?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firebaseRepository.deletePictureOfTheDay(picture?.id ?: "", {
                    isPictureAdded.value = false
                }, {
                    _state.value = PictureState.PictureError("Error: $it")
                })
            } catch (e: Exception) {
                _state.value = PictureState.PictureError("Error: ${e.message}")
            }
        }
    }
}