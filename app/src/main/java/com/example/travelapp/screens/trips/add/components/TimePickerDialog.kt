package com.example.travelapp.screens.trips.add.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit
) {
    val state = rememberTimePickerState()
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.tertiary
            ),
                onClick = onDismissRequest
            ) {
                Text("Cancel")
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary
                ),
                onClick = {
                var hour = "${state.hour}"
                var min = "${state.minute}"
                if(state.hour < 10 ) {
                    hour = "0${state.hour}"
                }
                if(state.minute < 10){
                    min = "0${state.minute}"
                }
                onTimeSelected(LocalTime.parse("$hour:$min:00"))
            }) {
                Text("OK")
            }
        },
        text = {
            TimePicker(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                colors = TimePickerDefaults.colors(
                    clockDialSelectedContentColor = White,
                    timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                    timeSelectorSelectedContentColor = White,
                    timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )
        }
    )
}