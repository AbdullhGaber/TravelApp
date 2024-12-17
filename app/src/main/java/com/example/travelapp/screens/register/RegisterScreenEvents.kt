package com.example.travelapp.screens.register

sealed class RegisterScreenEvents {
    data object Register : RegisterScreenEvents()
    data object ClearAuthFlowState : RegisterScreenEvents()
}