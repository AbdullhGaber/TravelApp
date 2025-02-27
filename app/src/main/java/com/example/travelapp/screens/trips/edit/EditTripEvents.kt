package com.example.travelapp.screens.trips.edit

sealed class EditTripEvents {
    data object OnEditButtonClick : EditTripEvents()
    data object OnErrorDialogDismiss : EditTripEvents()
}