package com.example.travelapp.screens.register

import android.content.ContentResolver
import android.net.Uri

sealed class RegisterScreenEvents {
    data object OnSubmitButtonClick : RegisterScreenEvents()
    data object OnErrorDismiss : RegisterScreenEvents()
    data class OnChooseImageClick(val contentResolver: ContentResolver) : RegisterScreenEvents()
}