package com.application.tripapp.ui.main.images.imagepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.usecase.LoadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ImagePageViewModel@Inject constructor(
    private val useCase: LoadImageUseCase
) : ViewModel() {


    private val _state = MutableStateFlow<ImagePageState>(ImagePageState.PictureLoaded(null))
    val state: StateFlow<ImagePageState> = _state

    fun processAction(action: ImagePageAction) {
        when (action) {
            is ImagePageAction.LoadPictures -> {
                loadPictures(action.keyWord)
            }
            else -> {}
        }
    }

    private fun loadPictures(keyWord: String?) {
        if (keyWord != null) {
            viewModelScope.launch {
                useCase.getPictureById(keyWord).collect { image ->
                    _state.value = ImagePageState.PictureLoaded(image)
                }
        }
        }
    }
}