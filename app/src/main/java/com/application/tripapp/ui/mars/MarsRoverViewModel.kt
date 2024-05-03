package com.application.tripapp.ui.mars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.ui.picture.PictureAction
import com.application.tripapp.ui.picture.PictureState
import com.application.tripapp.usecase.LoadRoverPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarsRoverViewModel @Inject constructor(
    private val loadRoverPictureUseCase: LoadRoverPictureUseCase
) : ViewModel() {


    private val _state =
        MutableStateFlow<MarsRoverState>(MarsRoverState.Loading)
    val state: StateFlow<MarsRoverState> get() = _state


    fun processAction(action: MarsRoverAction, date: String) {
        when (action) {
            is MarsRoverAction.LoadPicture -> loadImages(date)
            is MarsRoverAction.Loading -> loading()
            else -> {}
        }
    }

    fun loading() {
        viewModelScope.launch {
            try {
                _state.value = MarsRoverState.Loading
            } catch (e: Exception) {
                _state.value = MarsRoverState.PicturesError("Error: ${e.message}")
            }
        }
    }


    fun loadImages(date: String) {
        viewModelScope.launch {
            try {
                loadRoverPictureUseCase.getPicture(date).collect { pictures ->
                    _state.value = MarsRoverState.PicturesLoaded(pictures)
                }

            } catch (e: Exception) {
                _state.value = MarsRoverState.PicturesError("Error: ${e.message}")
            }
        }
    }
}