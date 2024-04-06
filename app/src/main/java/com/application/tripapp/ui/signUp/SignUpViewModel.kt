package com.application.tripapp.ui.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.ui.login.LoginAction
import com.application.tripapp.ui.login.LoginState
import com.application.tripapp.ui.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: FireBaseRepository) :
    ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Uninitialized)
    val state: StateFlow<SignUpState> = _state

    fun processAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.SignUp -> {
                signUp(
                    action.email, action.password
                )
            }
        }
    }

    private fun signUp(email: String, password: String) {
        repository.signUp(email, password, {
            _state.value = SignUpState.SignUpSuccess
        }, {
            _state.value = SignUpState.Error(it)
        })
    }
}
