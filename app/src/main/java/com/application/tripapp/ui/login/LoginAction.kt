package com.application.tripapp.ui.login

sealed class LoginAction {
    class SignIn(
        val email: String,
        val password: String
    ) : LoginAction()
}