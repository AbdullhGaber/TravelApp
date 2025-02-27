package com.example.travelapp.screens.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.domain.entity.TripEntity
import com.example.travelapp.R
import com.example.travelapp.ui.theme.LightBlue
import com.example.travelapp.ui.theme.TravelAppTheme

@Composable
fun TripReminderDialog(
    modifier : Modifier = Modifier,
    trip : TripEntity = TripEntity(),
    onStartClick : () -> Unit = {},
    onLaterClick : () -> Unit = {},
    onCancelClick : () -> Unit = {},
){
    Dialog(
        onDismissRequest = {}
    ) {
        Column(
            modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.secondary).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = trip.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(Modifier.height(10.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Icon(
                    painter = painterResource(R.drawable.map_ic),
                    modifier = Modifier.size(50.dp),
                    tint = LightBlue,
                    contentDescription = "Location Icon"
                )


                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(R.drawable.earth_ic),
                    contentDescription = "Location Icon",
                )

                Icon(
                    painter = painterResource(R.drawable.map_ic),
                    tint = LightBlue,
                    modifier = Modifier.size(50.dp).padding(0.dp),
                    contentDescription = "Location Icon"
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth().padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = trip.startDestination,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                )

                Text(
                    text = trip.endDestination,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.align(Alignment.Start).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TripReminderDialogButton(
                    text = stringResource(R.string.cancel),
                    onClick = onCancelClick
                )

                Row {
                    TripReminderDialogButton(
                        text = stringResource(R.string.later),
                        onClick = onLaterClick
                    )

                    Spacer(Modifier.width(8.dp))

                    TripReminderDialogButton(
                        text = stringResource(R.string.start),
                        onClick = onStartClick
                    )
                }
            }
        }
    }
}

@Composable
private fun TripReminderDialogButton(
    text : String,
    onClick : () -> Unit
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondary, contentColor = Color.White)
    ) {
        Text(text = text)
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PreviewTripReminderDialog(){
      TravelAppTheme {
          TripReminderDialog(
              trip = TripEntity(name = "Vacation" , startDestination = "Cairo" , endDestination = "Alexandria")
          )
      }
}