package com.example.travelapp.screens.trips.add.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.travelapp.R
import com.example.travelapp.screens.common.TripTextField
import com.example.travelapp.screens.trips.add.AddTripViewModel

@Composable
fun TripTypeForm(
    viewModel: AddTripViewModel
){
    Column{
        TripTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = stringResource(R.string.trip_date),
            value = viewModel.roundTripDateState.value,
            onValueChange = {
                viewModel.roundTripDateErrorState.value = ""
                viewModel.roundTripDateState.value = it
            },
            isError = viewModel.roundTripDateErrorState.value.isNotEmpty(),
            errorMessage = viewModel.roundTripDateErrorState.value,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_month_ic),
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
            placeholder = stringResource(R.string.trip_time),
            value = viewModel.roundTripTimeState.value,
            onValueChange = {
                viewModel.roundTripTimeErrorState.value = ""
                viewModel.roundTripTimeState.value = it
            },
            isError = viewModel.roundTripTimeErrorState.value.isNotEmpty(),
            errorMessage = viewModel.roundTripTimeErrorState.value,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.time_ic),
                    contentDescription = stringResource(
                        R.string.airplane_icon
                    ),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
    }
}