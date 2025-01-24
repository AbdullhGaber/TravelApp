package com.example.travelapp.screens.trips.add

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.data.mapper.localDateToMillis
import com.example.data.mapper.textToLocalDate
import com.example.domain.use_cases.trip.TripUseCases
import com.example.travelapp.utils.areAddTripFieldsValid
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val showSingleTripDatePickerDialog  = mutableStateOf(false)
    val selectedSingleTripDate = mutableStateOf<String?>(null)
    val selectedSingleDateErrorState = mutableStateOf("")

    val showSingleTripTimePickerDialog = mutableStateOf(false)
    val selectedSingleTripTime = mutableStateOf<String?>(null)
    val selectedSingleTimeErrorState = mutableStateOf("")

    val roundTripShowDatePickerDialog  = mutableStateOf(false)
    val roundTripSelectedDate = mutableStateOf<String?>(null)
    val roundTripSelectedDateErrorState = mutableStateOf("")

    val roundTripShowTimePickerDialog = mutableStateOf(false)
    val roundTripSelectedTime = mutableStateOf<String?>(null)
    val roundTripSelectedTimeErrorState = mutableStateOf("")

    val isExpanded = mutableStateOf(false)
    val isRoundTrip = mutableStateOf(false)

    private fun areFieldsValid() : Boolean{
        val startLocalDate = textToLocalDate(selectedSingleTripDate.value)
        val startMillis = localDateToMillis(startLocalDate)

        val endLocalDate = textToLocalDate(roundTripSelectedDate.value)
        val endMillis = localDateToMillis(endLocalDate)

        return areAddTripFieldsValid(
            tripName = tripNameState.value,
            tripNameErrorState = tripNameErrorState,
            startPoint = tripStartPState.value,
            startPointErrorState = tripStartPErrorState,
            endPoint = tripEndPState.value,
            endPointErrorState = tripEndPErrorState,
            startDateMillis = startMillis,
            dateErrorState = selectedSingleDateErrorState,
            startTime = selectedSingleTripTime.value,
            startTimeErrorState = selectedSingleTimeErrorState,
            endDateMillis = endMillis,
            isRoundTrip = isRoundTrip.value,
            roundTripDateErrorState = roundTripSelectedDateErrorState,
            endTripTime = roundTripSelectedTime.value,
            endTripTimeErrorState = roundTripSelectedTimeErrorState,
        )
    }

    fun isNoErrors() : Boolean{
        return if((isRoundTrip.value)){
            ((tripStartPState.value.isNotEmpty() && tripStartPErrorState.value.isEmpty())
                    && (tripEndPState.value.isNotEmpty() && tripEndPErrorState.value.isEmpty())
                    && (tripNameState.value.isNotEmpty() && tripNameErrorState.value.isEmpty())
                    && (selectedSingleTripDate.value != null && selectedSingleDateErrorState.value.isEmpty())
                    && (selectedSingleTripTime.value != null && selectedSingleTimeErrorState.value.isEmpty())
                    && (roundTripSelectedDate.value != null && roundTripSelectedDateErrorState.value.isEmpty())
                    && (roundTripSelectedTime.value != null && roundTripSelectedTimeErrorState.value.isEmpty()))
        }else{
            ((tripStartPState.value.isNotEmpty() && tripStartPErrorState.value.isEmpty())
                    && (tripEndPState.value.isNotEmpty() && tripEndPErrorState.value.isEmpty())
                    && (tripNameState.value.isNotEmpty() && tripNameErrorState.value.isEmpty())
                    && (selectedSingleTripDate.value != null && selectedSingleDateErrorState.value.isEmpty())
                    && (selectedSingleTripTime.value != null && selectedSingleTimeErrorState.value.isEmpty()))
        }

    }
}