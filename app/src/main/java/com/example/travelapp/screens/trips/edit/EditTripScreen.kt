package com.example.travelapp.screens.trips.edit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.uitls.Resource
import com.example.travelapp.R
import com.example.travelapp.screens.common.ErrorDialog
import com.example.travelapp.screens.common.PrimaryButton
import com.example.travelapp.screens.common.ScreenHeaderImage
import com.example.travelapp.screens.common.TripCircularProgressIndicator
import com.example.travelapp.screens.common.TripTypeDropDownMenu
import com.example.travelapp.screens.trips.add.components.TripTypeForm
import com.example.travelapp.screens.trips.edit.components.EditTripForm
import com.example.travelapp.screens.trips.edit.components.EditTripTypeForm
import com.example.travelapp.ui.theme.TravelAppTheme
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun EditTripScreen(
    viewModel: EditTripViewModel = hiltViewModel(),
    navigateUp : () -> Unit,
    tripId : String,
    uid : String
){
   Column(
       Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondary),
   ){
       ScreenHeaderImage(R.drawable.add_trip_header)
       Spacer(Modifier.height(8.dp))

       val trip by viewModel.tripState

       LaunchedEffect(Unit){
           viewModel.getTripById(tripId,uid)
       }

       val context = LocalContext.current

       LaunchedEffect(Unit){
           viewModel.editTripSharedFlow.distinctUntilChanged().collect{ message ->
               Toast.makeText(context, message,Toast.LENGTH_LONG).show()
               navigateUp()
           }
       }

       when(trip){
           is Resource.Loading -> {
               Box(
                   Modifier.
                   fillMaxSize().
                   background(Color.Black.copy(alpha = 0.3f)).
                   clickable(enabled = false) {}
               ){
                   TripCircularProgressIndicator(
                       modifier = Modifier.align(Alignment.Center)
                   )
               }
           }

           is Resource.Success -> {
               EditTripForm(viewModel)

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

               if (viewModel.isRoundTrip.value) {
                   Spacer(modifier = Modifier.height(32.dp))
                   EditTripTypeForm(viewModel)
                   Spacer(modifier = Modifier.height(32.dp))
               }

               Spacer(modifier = Modifier.height(40.dp))


               PrimaryButton(
                   modifier = Modifier
                       .padding(horizontal = 16.dp)
                       .fillMaxWidth(),
                   enabled = viewModel.isNoErrors(),
                   onClick = {
                        viewModel.onEvent(EditTripEvents.OnEditButtonClick)
                   },
                   text = stringResource(id = R.string.edit)
               )

               Spacer(modifier = Modifier.height(30.dp))
           }

           is Resource.Failure -> {
               ErrorDialog(
                   text = trip.message!!,
                   onDismiss = {
                       viewModel.onEvent(EditTripEvents.OnErrorDialogDismiss)
                       navigateUp()
                   }
               )
           }
           else -> Unit
       }

   }
}

@Composable
@Preview
fun PreviewEditTripScreen(){
    TravelAppTheme {
//        EditTripScreen()
    }
}
