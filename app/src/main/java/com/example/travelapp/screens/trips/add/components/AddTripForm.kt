package com.example.travelapp.screens.trips.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                viewModel.showDatePickerDialog.value = true
            }
            .background(Transparent)
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.calendar_month_ic),
                contentDescription = stringResource(
                    R.string.airplane_icon
                ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = viewModel.selectedDate.value ?: "Pick a Date",
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        if (viewModel.showDatePickerDialog.value) {
            DatePickerDialog(
                onDismissRequest = { viewModel.showDatePickerDialog.value = false },
                onDateSelected = { date ->
                    viewModel.selectedDate.value = "${date.dayOfMonth}-${date.monthValue}-${date.year}"
                    viewModel.showDatePickerDialog.value = false
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                viewModel.showTimePickerDialog.value = true
            }
            .background(Transparent)
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.time_ic),
                contentDescription = stringResource(
                    R.string.airplane_icon
                ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = viewModel.selectedTime.value ?: "Pick a Time",
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (viewModel.showTimePickerDialog.value) {
        TimePickerDialog(
            onDismissRequest = { viewModel.showTimePickerDialog.value = false },
            onTimeSelected = { time ->
                viewModel.selectedTime.value = time.format(DateTimeFormatter.ofPattern("hh:mm a"))
                viewModel.showTimePickerDialog.value = false
            }
        )
    }


    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(R.string.trip_type),
        color = MaterialTheme.colorScheme.tertiary
    )

}