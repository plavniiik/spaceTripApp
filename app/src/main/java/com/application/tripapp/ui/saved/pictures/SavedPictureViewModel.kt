package com.application.tripapp.ui.saved.pictures

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
@HiltViewModel
class SavedPictureViewModel @Inject constructor(private val repository: FireBaseRepository) :
    ViewModel() {
    private val disposable = CompositeDisposable()
    val state = MutableLiveData<SavedPicturesState>()


    fun processAction(action: SavedPicturesAction) {
        when (action) {
            is SavedPicturesAction.Load -> {
                loadPictures()
            }
        }
    }

    private fun loadPictures() {
        repository.getPicturesOfTheDay()
        disposable.add(
            repository.picturesSubject
                .doOnSubscribe { Log.d("SavedPictureViewModel", "Subscribed") }
                .doOnError { error -> Log.e("SavedPictureViewModel", "Error", error) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { pictures ->
                        Log.d("SavedPictureViewModel", "Received pictures: ${pictures.size}")
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
                    { error ->
                        Log.e("SavedPictureViewModel", "Error in subscription", error)
                        state.postValue(SavedPicturesState.Error(error.localizedMessage))
                    }
                )
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}