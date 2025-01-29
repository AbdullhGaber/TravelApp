package com.example.travelapp.screens.trips.add.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.data.mapper.localDateToMillis
import com.example.data.mapper.localTimeToText
import com.example.data.mapper.textToLocalDate
import com.example.travelapp.R
import com.example.travelapp.screens.common.TripTextField
import com.example.travelapp.screens.trips.add.AddTripViewModel
import com.example.travelapp.utils.isEndPointValid
import com.example.travelapp.utils.isStartPointValid
import com.example.travelapp.utils.isTripEndPointDateValid
import com.example.travelapp.utils.isTripEndTimeValid
import com.example.travelapp.utils.isTripNameValid
import com.example.travelapp.utils.isTripStartPointDateValid
import com.example.travelapp.utils.isTripStartTimeValid


@Composable
fun AddTripForm(
    viewModel : AddTripViewModel
) {
    TripTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        placeholder = stringResource(R.string.trip_start_point),
        value = viewModel.tripStartPState.value,
        onValueChange = {
            viewModel.tripStartPErrorState.value = ""
            viewModel.tripStartPState.value = it
            isStartPointValid(it,viewModel.tripStartPErrorState)
        },
        isError = viewModel.tripStartPErrorState.value.isNotEmpty(),
        errorMessage = viewModel.tripStartPErrorState.value,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.airplane_ic),
                contentDescription = stringResource(
                    R.string.airplane_icon
                ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    TripTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        placeholder = stringResource(R.string.trip_end_point),
        value = viewModel.tripEndPState.value,
        onValueChange = {
            viewModel.tripEndPErrorState.value = ""
            viewModel.tripEndPState.value = it
            isEndPointValid(it,viewModel.tripEndPErrorState)
        },
        isError = viewModel.tripEndPErrorState.value.isNotEmpty(),
        errorMessage = viewModel.tripEndPErrorState.value,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.airplane_ic),
                contentDescription = stringResource(
                    R.string.airplane_icon
                ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    TripTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        placeholder = stringResource(R.string.trip_name),
        value = viewModel.tripNameState.value,
        onValueChange = {
            viewModel.tripNameErrorState.value = ""
            viewModel.tripNameState.value = it
            isTripNameValid(it,viewModel.tripNameErrorState)
        },
        isError = viewModel.tripNameErrorState.value.isNotEmpty(),
        errorMessage = viewModel.tripNameErrorState.value,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.edit_ic),
                contentDescription = stringResource(
                    R.string.airplane_icon
                ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    val context = LocalContext.current

    PickDateField(
        selectedDate = viewModel.selectedSingleTripDate.value,
        showDatePickerDialog = viewModel.showSingleTripDatePickerDialog.value,
        onClick = {
            viewModel.showSingleTripDatePickerDialog.value = true
        },
        onDateSelected = {date, selectedMillis ->
            viewModel.selectedSingleTripDate.value = "${date.year}-${date.monthValue}-${date.dayOfMonth}"
            if(viewModel.roundTripSelectedDate.value != null){
                val secondTripLocalDate = textToLocalDate(viewModel.roundTripSelectedDate.value)
                val secondDateMillis = localDateToMillis(secondTripLocalDate)
                isTripEndPointDateValid(
                    firstTripDateMillis = selectedMillis,
                    secondTripDateMillis = secondDateMillis,
                    viewModel.roundTripSelectedDateErrorState
                )
            }

            if(viewModel.selectedSingleTripTime.value != null){
                val localDate = textToLocalDate(viewModel.selectedSingleTripDate.value)
                val dateMillis = localDateToMillis(localDate)
                isTripStartTimeValid(
                    time = viewModel.selectedSingleTripTime.value,
                    selectedDateMillis = dateMillis,
                    timeErrorState = viewModel.selectedSingleTimeErrorState
                )
            }

            if(viewModel.roundTripSelectedTime.value != null){
                val startLocalDate = textToLocalDate(viewModel.selectedSingleTripDate.value)
                val startDateMillis = localDateToMillis(startLocalDate)

                val endLocalDate = textToLocalDate(viewModel.roundTripSelectedDate.value)
                val endDateMillis = localDateToMillis(endLocalDate)

                isTripEndTimeValid(
                    endTime =viewModel.roundTripSelectedTime.value,
                    startTime = viewModel.selectedSingleTripTime.value,
                    selectedStartDateMillis = startDateMillis,
                    selectedEndDateMillis = endDateMillis,
                    timeErrorState = viewModel.roundTripSelectedTimeErrorState
                )
            }

            isTripStartPointDateValid(
                selectedMillis = selectedMillis,
                selectedDateErrorState = viewModel.selectedSingleDateErrorState
            )

            viewModel.selectedSingleTripDate.value = "${date.year}-${date.monthValue}-${date.dayOfMonth}"
            viewModel.showSingleTripDatePickerDialog.value = false
        },
        onDismissRequest = {
            if(viewModel.selectedSingleTripDate.value == null){
                viewModel.selectedSingleDateErrorState.value =
                    context.getString(R.string.date_field_is_required)
            }
            viewModel.showSingleTripDatePickerDialog.value = false
        },
        error = viewModel.selectedSingleDateErrorState.value
    )

    Spacer(modifier = Modifier.height(16.dp))

    PickTimeField(
        selectedTime = viewModel.selectedSingleTripTime.value,
        showTimePickerDialog = viewModel.showSingleTripTimePickerDialog.value,
        onClick = {
            viewModel.showSingleTripTimePickerDialog.value = true
        },
        error = viewModel.selectedSingleTimeErrorState.value,
        onTimeSelected = {time ->
            viewModel.selectedSingleTripTime.value = localTimeToText(time)
            if(viewModel.roundTripSelectedTime.value != null){
                val firstTripLocalDate = textToLocalDate(viewModel.selectedSingleTripDate.value)
                val firstDateMillis = localDateToMillis(firstTripLocalDate)

                val secondTripLocalDate = textToLocalDate(viewModel.roundTripSelectedDate.value)
                val secondDateMillis = localDateToMillis(secondTripLocalDate)
                isTripEndTimeValid(
                    endTime = viewModel.roundTripSelectedTime.value,
                    startTime = viewModel.selectedSingleTripTime.value,
                    selectedStartDateMillis = firstDateMillis,
                    selectedEndDateMillis = secondDateMillis,
                    timeErrorState = viewModel.roundTripSelectedTimeErrorState
                )
            }
            val localDate = textToLocalDate(viewModel.selectedSingleTripDate.value)
            val dateMillis = localDateToMillis(localDate)
            isTripStartTimeValid(viewModel.selectedSingleTripTime.value,dateMillis,viewModel.selectedSingleTimeErrorState)
            viewModel.showSingleTripTimePickerDialog.value = false
        },
        onDismissRequest = {
            if(viewModel.selectedSingleTripTime.value == null){
                isTripStartTimeValid(viewModel.selectedSingleTripTime.value,0L,viewModel.selectedSingleTimeErrorState)
            }
            viewModel.showSingleTripTimePickerDialog.value = false
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(R.string.trip_type),
        color = MaterialTheme.colorScheme.tertiary
    )

}