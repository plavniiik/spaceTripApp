package com.application.tripapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.model.PictureOfTheDay
import com.application.tripapp.repository.PictureOfTheDayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PictureOfTheDayRepository):
    ViewModel() {

    val state = MutableLiveData<MainState>()

    fun processAction(action: MainAction) {
        when (action) {
            is MainAction.LoadPicture -> {
                loadPicture()
            }
        }
    }
    private fun loadPicture() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getPicture()
                if (response.isSuccessful) {
                    response.body()?.let {
                        val picture = PictureOfTheDay(
                            it.explanation,
                            it.title,
                            it.url
                        )
                        state.postValue(
                            picture?.let { MainState.PictureLoaded(picture) }
                        )
                    }
                }
            } catch (e: Exception) {
                state.postValue(MainState.PictureError("Error: ${e.message}"))
            }
        }
    }
}