package com.example.travelapp.screens.trips.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.travelapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripTypeDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded : Boolean,
    onItemClick : (String) -> Unit,
    onExpandedChange : (Boolean) -> Unit,
    onDismissRequest : () -> Unit
) {
    val tripTypes = listOf(
        stringResource(id = R.string.one_direction_trip),
        stringResource(id = R.string.round_trip)
    )


    val selectedItem = remember { mutableStateOf(tripTypes[0]) }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.tertiary),
        expanded = isExpanded,
        onExpandedChange = onExpandedChange
    ){
        TextField(
            value = selectedItem.value,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                unfocusedContainerColor = Transparent,
                focusedContainerColor = Transparent
            ),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier
                .menuAnchor()
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            modifier = modifier.background(MaterialTheme.colorScheme.secondary),
            expanded = isExpanded,
            onDismissRequest = onDismissRequest
        ) {
            tripTypes.forEach { tripType ->
                DropdownMenuItem(
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    text = { Text(tripType) },
                    onClick = {
                        selectedItem.value = tripType
                        onItemClick(tripType)
                    }
                )
            }
        }
    }
}