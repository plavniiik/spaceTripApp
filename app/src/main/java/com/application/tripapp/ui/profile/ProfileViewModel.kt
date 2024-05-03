package com.application.tripapp.ui.profile

import androidx.lifecycle.ViewModel
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.repository.PictureOfTheDayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepository: FireBaseRepository
) :
    ViewModel() {

    fun logout() {
        firebaseRepository.signOut()
    }

}