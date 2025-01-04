package com.example.travelapp.screens.trips.add

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTripViewModel @Inject constructor(

) : ViewModel() {
    val tripStartPState = mutableStateOf("")
    val tripStartPErrorState = mutableStateOf("")

    val tripEndPState = mutableStateOf("")
    val tripEndPErrorState = mutableStateOf("")

    val tripNameState = mutableStateOf("")
    val tripNameErrorState = mutableStateOf("")

    val tripDateState = mutableStateOf("")
    val tripDateErrorState = mutableStateOf("")

    val tripTimeState = mutableStateOf("")
    val tripTimeErrorState = mutableStateOf("")

    val roundTripDateState = mutableStateOf("")
    val roundTripDateErrorState = mutableStateOf("")

    val roundTripTimeState = mutableStateOf("")
    val roundTripTimeErrorState = mutableStateOf("")

    val isExpanded = mutableStateOf(false)
    val isRoundTrip = mutableStateOf(false)
}