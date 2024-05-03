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
import com.application.tripapp.repository.PicturePagingSourceFactory
import com.application.tripapp.ui.main.images.ImageState
import com.application.tripapp.usecase.LoadPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadPictureUseCase: LoadPictureUseCase,
    private val pagingSourceFactory: PicturePagingSourceFactory

) :
    ViewModel() {

    private val galaxy = "galaxy"
    private val astronomy = "moonlight"
    private var _pagingSource: PicturePagingSource? = null
    private val _stateGalaxy = MutableStateFlow<PagingData<Picture>>(PagingData.empty())
    val stateGalaxy: StateFlow<PagingData<Picture>> = _stateGalaxy


    private val _stateAstronomy = MutableStateFlow<PagingData<Picture>>(PagingData.empty())
    val stateAstronomy: StateFlow<PagingData<Picture>> = _stateAstronomy

    private val _state = MutableStateFlow<MainState>(MainState.PictureLoaded(null))
    val state: StateFlow<MainState> = _state

    init {
        loadPictures(galaxy)
        loadPictures(astronomy)
    }

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

    private fun loadPictures(keyWord: String?) {
        if (keyWord != null) {
            _pagingSource = pagingSourceFactory.create(keyWord)
        }
        val pager: Flow<PagingData<Picture>> =
            Pager(PagingConfig(pageSize = 50, initialLoadSize = 50)) {
                _pagingSource!!
            }.flow.cachedIn(viewModelScope)

        viewModelScope.launch {
            pager.collectLatest { pagingData ->
                if (keyWord == galaxy) {
                    _stateGalaxy.value = pagingData
                } else if (keyWord == astronomy) {
                    _stateAstronomy.value = pagingData
                }
            }
        }
    }
}
