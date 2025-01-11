package com.example.travelapp.screens.trips.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.secondary,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary
                ),
                onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        val selectedDate = Instant.ofEpochMilli(selectedMillis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selectedDate)
                    }
                    onDismissRequest()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary
                ),
            ) {
                Text("Cancel")
            }
        },
        text = {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor =  MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary,
                    headlineContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    yearContentColor = White,
                    selectedYearContentColor = Gray,
                    currentYearContentColor =  White,
                    navigationContentColor = MaterialTheme.colorScheme.tertiary,
                    todayDateBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    todayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedDayContentColor = MaterialTheme.colorScheme.secondary,
                    selectedDayContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,

                ),
            )
        }
    )
}