package com.example.travelapp.screens.register

import android.content.ContentResolver
import android.net.Uri

sealed class RegisterScreenEvents {
    data object Register : RegisterScreenEvents()
    data object ClearAuthFlowState : RegisterScreenEvents()
    data class OnChooseImageClick(val contentResolver: ContentResolver, val uri : Uri) : RegisterScreenEvents()
}