package com.application.tripapp.ui.login

sealed class LoginState {

    data object LoginSuccess : LoginState()
    class Error(val error: String) : LoginState()
    object Uninitialized : LoginState()
}