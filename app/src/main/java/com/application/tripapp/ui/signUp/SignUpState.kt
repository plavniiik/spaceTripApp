package com.application.tripapp.ui.signUp

import com.application.tripapp.ui.login.LoginState

sealed class SignUpState {
    data object SignUpSuccess : SignUpState()
    class Error(val error: String) : SignUpState()
    object Uninitialized : SignUpState()
}