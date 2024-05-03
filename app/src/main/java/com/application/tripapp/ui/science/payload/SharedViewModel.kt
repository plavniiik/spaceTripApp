package com.application.tripapp.ui.science.payload

import androidx.lifecycle.ViewModel
import com.application.tripapp.model.Payload
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    val selectedPayload = MutableStateFlow<Payload?>(null)

    fun selectPayload(payload: Payload) {
        selectedPayload.value = payload
    }
}