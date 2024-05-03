package com.application.tripapp.ui.main.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.application.tripapp.model.Picture
import com.application.tripapp.repository.PicturePagingSource
import com.application.tripapp.repository.PicturePagingSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val pagingSourceFactory: PicturePagingSourceFactory
) : ViewModel() {

    private var _pagingSource: PicturePagingSource? = null
    private val _state = MutableStateFlow<ImageState>(ImageState.PicturesLoaded(PagingData.empty()))
    val state: StateFlow<ImageState> = _state

    fun processAction(action: ImageAction) {
        when (action) {
            is ImageAction.LoadPictures -> {
                loadPictures(action.keyWord)
            }
            else -> {}
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

                if (pagingData==null) {
                    _state.value = ImageState.PicturesError("No pictures found")}
                    else{
                    _state.value = ImageState.PicturesLoaded(pagingData)
                    }

            }
        }
    }
}