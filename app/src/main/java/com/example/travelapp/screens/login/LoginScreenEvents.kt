package com.example.travelapp.screens.login

import com.example.travelapp.screens.register.RegisterScreenEvents

sealed class LoginScreenEvents {
    data object Login : LoginScreenEvents()
    data object ClearAuthFlowState : LoginScreenEvents()
}