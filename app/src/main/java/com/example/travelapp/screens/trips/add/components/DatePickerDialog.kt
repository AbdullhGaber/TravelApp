package com.example.travelapp.screens.trips.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Allows custom width
    ) {
        Box(
            modifier = Modifier.fillMaxSize() ,// Add padding to avoid edge clipping
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.secondary
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    DatePicker(
                        modifier = Modifier.fillMaxWidth(),
                        state = datePickerState,
                        colors = DatePickerDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.tertiary,
                            headlineContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            yearContentColor = Color.White,
                            selectedYearContentColor = Color.Gray,
                            currentYearContentColor = Color.White,
                            navigationContentColor = MaterialTheme.colorScheme.tertiary,
                            todayDateBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            todayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedDayContentColor = MaterialTheme.colorScheme.secondary,
                            selectedDayContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = onDismissRequest,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.tertiary
                            ),
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            onClick = {
                                val selectedMillis = datePickerState.selectedDateMillis
                                if (selectedMillis != null) {
                                    val selectedDate = Instant.ofEpochMilli(selectedMillis)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                    onDateSelected(selectedDate)
                                }
                                onDismissRequest()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

