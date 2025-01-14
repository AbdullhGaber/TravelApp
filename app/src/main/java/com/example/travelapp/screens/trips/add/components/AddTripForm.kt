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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.travelapp.R
import com.example.travelapp.screens.common.TripTextField
import com.example.travelapp.screens.trips.add.AddTripViewModel
import java.time.format.DateTimeFormatter

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

    PickDateField(
        selectedDate = viewModel.selectedDate.value,
        showDatePickerDialog = viewModel.showDatePickerDialog.value,
        onClick = {
            viewModel.showDatePickerDialog.value = true
        },
        onDateSelected = {date ->
            viewModel.selectedDate.value = "${date.dayOfMonth}-${date.monthValue}-${date.year}"
            viewModel.showDatePickerDialog.value = false
        },
        onDismissRequest = {
            viewModel.showDatePickerDialog.value = false
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    PickTimeField(
        selectedTime = viewModel.selectedTime.value,
        showTimePickerDialog = viewModel.showTimePickerDialog.value,
        onClick = {
            viewModel.showTimePickerDialog.value = true
        },
        onTimeSelected = {time ->
            viewModel.selectedTime.value = time.format(DateTimeFormatter.ofPattern("hh:mm a"))
            viewModel.showTimePickerDialog.value = false
        },
        onDismissRequest = {
            viewModel.showTimePickerDialog.value = false
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(R.string.trip_type),
        color = MaterialTheme.colorScheme.tertiary
    )

}