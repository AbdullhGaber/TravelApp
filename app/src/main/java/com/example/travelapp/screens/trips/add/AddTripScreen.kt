package com.example.travelapp.screens.trips.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.entity.TripEntity
import com.example.travelapp.R
import com.example.travelapp.screens.common.PrimaryButton
import com.example.travelapp.screens.common.TripTextField
import com.example.travelapp.ui.theme.TravelAppTheme

@Composable
fun AddTripScreen(
    viewModel : AddTripViewModel = hiltViewModel()
){
      val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .verticalScroll(scrollState)
    ){
        ScreenHeaderImage()

        Spacer(modifier = Modifier.height(32.dp))

        AddTripForm(viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        val context = LocalContext.current

        TripTypeDropDownMenu(
            isExpanded = viewModel.isExpanded.value,
            onItemClick = { tripType ->
                viewModel.isRoundTrip.value = tripType == context.getString(R.string.round_trip)
                viewModel.isExpanded.value = false
            },
            onExpandedChange = {
                viewModel.isExpanded.value = it
            },
            onDismissRequest = {
                viewModel.isExpanded.value = false
            }
        )

        if(viewModel.isRoundTrip.value){
            Spacer(modifier = Modifier.height(16.dp))
            TripTypeForm(viewModel)
        }

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onClick = {},
            text = stringResource(id = R.string.add)
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun TripTypeForm(
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

@Composable
private fun AddTripForm(
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

    Spacer(modifier = Modifier.height(8.dp))

    TripTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        placeholder = stringResource(R.string.trip_date),
        value = viewModel.tripDateState.value,
        onValueChange = {
            viewModel.tripDateErrorState.value = ""
            viewModel.tripDateState.value = it
        },
        isError =  viewModel.tripDateErrorState.value.isNotEmpty(),
        errorMessage = viewModel.tripDateErrorState.value,
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
        value = viewModel.tripTimeState.value,
        onValueChange = {
            viewModel.tripTimeErrorState.value = ""
            viewModel.tripTimeState.value = it
        },
        isError = viewModel.tripTimeErrorState.value.isNotEmpty(),
        errorMessage = viewModel.tripTimeErrorState.value,
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

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(R.string.trip_type),
        color = MaterialTheme.colorScheme.tertiary
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TripTypeDropDownMenu(
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



@Composable
private fun ScreenHeaderImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.add_trip_header),
            contentDescription = stringResource(R.string.add_trip_screen_header)
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            text = stringResource(R.string.let_s_go),
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
fun PreviewAddTripScreen(){
    TravelAppTheme {
        AddTripScreen()
    }
}