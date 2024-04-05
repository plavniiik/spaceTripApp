package com.application.tripapp.ui.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.ui.login.LoginAction
import com.application.tripapp.ui.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: FireBaseRepository) :
    ViewModel() {

    val state = MutableLiveData<SignUpState>()

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
            state.value = SignUpState.SignUpSuccess
        }, {
            state.value = SignUpState.Error(it)
        })
    }
}