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
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R
import java.time.LocalTime

@Composable
fun PickTimeField(
    selectedTime : String?,
    showTimePickerDialog: Boolean,
    error : String,
    onClick : () -> Unit,
    onTimeSelected : (LocalTime) -> Unit,
    onDismissRequest : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                onClick()
            }
            .background(Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.time_ic),
                contentDescription = stringResource(
                    R.string.airplane_icon
                ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = selectedTime ?: "Pick a Time",
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primaryContainer
        )

        Spacer(modifier = Modifier.height(8.dp))

        if(error.isNotEmpty()){
            Text(
                text = error,
                color = Red,
                fontSize = 12.sp
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (showTimePickerDialog) {
        TimePickerDialog(
            onDismissRequest = onDismissRequest,
            onTimeSelected = onTimeSelected
        )
    }
}
