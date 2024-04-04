package com.application.tripapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.tripapp.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: FireBaseRepository) : ViewModel() {

    val state = MutableLiveData<LoginState>()

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
            state.value = LoginState.LoginSuccess
        }, {
            state.value = LoginState.Error(it)
        })
    }
}