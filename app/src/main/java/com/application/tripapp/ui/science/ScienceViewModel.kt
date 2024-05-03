package com.application.tripapp.ui.science

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.tripapp.model.Payload
import com.application.tripapp.ui.asteroids.AsteroidAction
import com.application.tripapp.ui.asteroids.AsteroidState
import com.application.tripapp.ui.science.payload.SharedViewModel
import com.application.tripapp.usecase.LoadAsteroidDataUseCase
import com.application.tripapp.usecase.LoadPayloadExperimentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScienceViewModel @Inject constructor(
    private val useCase: LoadPayloadExperimentUseCase,

) : ViewModel() {

    val selectedPayload = MutableStateFlow<Payload?>(null)
    private val _state = MutableStateFlow<ScienceState>(ScienceState.ScienceLoad)
    val state: StateFlow<ScienceState> get() = _state


    fun selectPayload(payload: Payload) {
        selectedPayload.value = payload
    }

    fun processAction(action: ScienceAction, id: String) {
        when (action) {
            is ScienceAction.LoadData -> loadData(id)
            else -> {}
        }
    }

    fun loadData(id: String) {
        viewModelScope.launch {
            try {
                useCase.getPayloadById(id).collect { payload ->
                    if (payload != null) {
                        _state.value = ScienceState.PayloadLoaded(payload)

                    } else {
                        _state.value = ScienceState.ScienceError("There is nothing")
                    }

                }
            } catch (e: Exception) {
                _state.value = ScienceState.ScienceError("Error: ${e.message}")
            }
        }
    }
}
