package com.application.tripapp.ui.signUp

import com.application.tripapp.ui.login.LoginAction

sealed class SignUpAction {
    class SignUp(
        val email: String,
        val password: String
    ) : SignUpAction()
}