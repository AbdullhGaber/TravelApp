package com.example.travelapp.screens.trips.add.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data.mapper.localDateToMillis
import com.example.data.mapper.localTimeToText
import com.example.data.mapper.textToLocalDate
import com.example.travelapp.screens.trips.add.AddTripViewModel
import com.example.travelapp.utils.isTripEndPointDateValid
import com.example.travelapp.utils.isTripEndTimeValid

@Composable
fun TripTypeForm(
    viewModel: AddTripViewModel
){
    Column{
        PickDateField(
            selectedDate = viewModel.roundTripSelectedDate.value,
            showDatePickerDialog = viewModel.roundTripShowDatePickerDialog.value,
            onClick = {
                viewModel.roundTripShowDatePickerDialog.value = true
            },
            onDateSelected = {date,selectedMillis ->
                if(viewModel.selectedSingleTripDate.value == null){
                    viewModel.roundTripSelectedDateErrorState.value = "Select trip start point date first"
                }else{
                    viewModel.roundTripSelectedDate.value = "${date.year}-${date.monthValue}-${date.dayOfMonth}"

                    val startLocalDate = textToLocalDate(viewModel.selectedSingleTripDate.value)
                    val startDateMillis = localDateToMillis(startLocalDate)

                    val endLocalDate = textToLocalDate(viewModel.roundTripSelectedDate.value)
                    val endDateMillis = localDateToMillis(endLocalDate)

                    isTripEndPointDateValid(
                        firstTripDateMillis = startDateMillis,
                        secondTripDateMillis = selectedMillis,
                        viewModel.roundTripSelectedDateErrorState
                    )

                    if(viewModel.roundTripSelectedTime.value != null){
                        isTripEndTimeValid(
                            endTime =viewModel.roundTripSelectedTime.value,
                            startTime = viewModel.selectedSingleTripTime.value,
                            selectedStartDateMillis = startDateMillis,
                            selectedEndDateMillis = endDateMillis,
                            timeErrorState = viewModel.roundTripSelectedTimeErrorState
                        )
                    }
                }
                viewModel.roundTripShowDatePickerDialog.value = false
            },
            onDismissRequest = {
                if(viewModel.roundTripSelectedDate.value == null){
                    viewModel.roundTripSelectedDateErrorState.value = "Round date field is required!"
                }
                viewModel.roundTripShowDatePickerDialog.value = false
            },
            error = viewModel.roundTripSelectedDateErrorState.value
        )

        Spacer(modifier = Modifier.height(16.dp))

        PickTimeField(
            selectedTime = viewModel.roundTripSelectedTime.value,
            showTimePickerDialog = viewModel.roundTripShowTimePickerDialog.value,
            onClick = {
                viewModel.roundTripShowTimePickerDialog.value = true
            },
            error = viewModel.roundTripSelectedTimeErrorState.value,
            onTimeSelected = {time ->
                viewModel.roundTripSelectedTime.value = localTimeToText(time)
                val secondsTripSelectedDate = textToLocalDate(viewModel.roundTripSelectedDate.value)
                val secondDateMillis = localDateToMillis(secondsTripSelectedDate)

                val firstTripDate = textToLocalDate(viewModel.selectedSingleTripDate.value)
                val firstDateMillis = localDateToMillis(firstTripDate)
                isTripEndTimeValid(
                    endTime = viewModel.roundTripSelectedTime.value,
                    startTime = viewModel.selectedSingleTripTime.value,
                    selectedStartDateMillis = firstDateMillis,
                    selectedEndDateMillis = secondDateMillis,
                    timeErrorState = viewModel.roundTripSelectedTimeErrorState
                )
                viewModel.roundTripShowTimePickerDialog.value = false
            },
            onDismissRequest = {
                if(viewModel.roundTripSelectedTime.value == null){
                    isTripEndTimeValid(
                        endTime = viewModel.roundTripSelectedTime.value,
                        startTime = viewModel.selectedSingleTripTime.value,
                        selectedStartDateMillis = 0L,
                        selectedEndDateMillis = 0L,
                        timeErrorState = viewModel.roundTripSelectedTimeErrorState
                    )
                }
                viewModel.roundTripShowTimePickerDialog.value = false
            }
        )
    }
}
