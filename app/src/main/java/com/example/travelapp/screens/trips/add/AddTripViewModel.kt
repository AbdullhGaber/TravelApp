package com.example.travelapp.screens.trips.add

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.domain.entity.TripEntity
import com.example.domain.use_cases.trip.TripUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddTripViewModel @Inject constructor(
    private val mTripUseCases : TripUseCases
) : ViewModel() {
    val tripStartPState = mutableStateOf("")
    val tripStartPErrorState = mutableStateOf("")

    val tripEndPState = mutableStateOf("")
    val tripEndPErrorState = mutableStateOf("")

    val tripNameState = mutableStateOf("")
    val tripNameErrorState = mutableStateOf("")

    var showDatePickerDialog  = mutableStateOf(false)
    var selectedDate = mutableStateOf<String?>(null)

    val showTimePickerDialog = mutableStateOf(false)
    val selectedTime = mutableStateOf<String?>(null)

    val roundTripDateState = mutableStateOf("")
    val roundTripDateErrorState = mutableStateOf("")

    val roundTripTimeState = mutableStateOf("")
    val roundTripTimeErrorState = mutableStateOf("")

    val isExpanded = mutableStateOf(false)
    val isRoundTrip = mutableStateOf(false)

}