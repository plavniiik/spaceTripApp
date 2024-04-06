package com.application.tripapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.ui.signUp.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: FireBaseRepository) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.Uninitialized)
    val state: StateFlow<LoginState> = _state


    fun processAction(action: LoginAction) {
        when (action) {
            is LoginAction.SignIn -> {
                signIn(
                    action.email, action.password
                )
            }
        }
    }

    private fun signIn(email: String, password: String) {
        repository.signIn(email, password, {
            _state.value = LoginState.LoginSuccess
        }, {
            _state.value = LoginState.Error(it)
        })
    }
}