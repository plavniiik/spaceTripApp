package com.application.tripapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.application.tripapp.model.Picture
import com.application.tripapp.repository.PicturePagingSource
import com.application.tripapp.usecase.LoadPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadPictureUseCase: LoadPictureUseCase,

) :
    ViewModel() {


    private val _state = MutableStateFlow<MainState>(MainState.PictureLoaded(null))
    val state: StateFlow<MainState> = _state

    fun processAction(action: MainAction) {
        when (action) {
            is MainAction.LoadPicture -> {
                loadPicture()
            }

            else -> {}
        }
    }

    private fun loadPicture() {
        if (_state.value == MainState.PictureLoaded(null)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    loadPictureUseCase.getPictureOfTheDayMain().collect { picture ->
                        _state.value = MainState.PictureLoaded(picture)
                    }
                } catch (e: Exception) {
                    _state.value = MainState.PictureError("Error: ${e.message}")
                }
            }
        }
    }
}