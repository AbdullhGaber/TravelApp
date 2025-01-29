@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.travelapp.screens.trips.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.uitls.Resource
import com.example.travelapp.R
import com.example.travelapp.screens.common.PrimaryButton
import com.example.travelapp.screens.common.TripCircularProgressIndicator
import com.example.travelapp.screens.trips.add.components.AddTripForm
import com.example.travelapp.screens.trips.add.components.ScreenHeaderImage
import com.example.travelapp.screens.trips.add.components.TripTypeDropDownMenu
import com.example.travelapp.screens.trips.add.components.TripTypeForm
import com.example.travelapp.ui.theme.TravelAppTheme
import com.example.travelapp.utils.hasPostNotificationPermission
import com.example.travelapp.utils.shouldShowPostNotificationRequestPermissionRationale

@Composable
fun AddTripScreen(
    viewModel : AddTripViewModel = hiltViewModel(),
    navigateUp : () -> Unit = {}
){
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()){
        val saveTripState = viewModel.saveTripState.collectAsState()

        when(saveTripState.value){
            is Resource.Loading -> {
                TripCircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is Resource.Failure -> {
                Toast.makeText(context,saveTripState.value.message,Toast.LENGTH_LONG).show()
            }

            is Resource.Success -> {
                Toast.makeText(context,saveTripState.value.data,Toast.LENGTH_LONG).show()
                navigateUp()
            }

            else -> Unit
        }

        AddTripContent(viewModel, navigateUp)
    }

}

@Composable
private fun AddTripContent(
    viewModel: AddTripViewModel,
    navigateUp: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .verticalScroll(scrollState)
    ) {
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

        if (viewModel.isRoundTrip.value) {
            Spacer(modifier = Modifier.height(32.dp))
            TripTypeForm(viewModel)
        }

        Spacer(modifier = Modifier.height(40.dp))

        val (notificationPermissionState, showNotificationRationale) = checkForNotificationPermission(navigateUp)
        val (exactAlarmPermissionState,showExactAlarmRationale) = checkForExactAlarmPermission(navigateUp)

        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            enabled = viewModel.isNoErrors(),
            onClick = {
                if (!notificationPermissionState.value || !exactAlarmPermissionState.value) {
                    if (!notificationPermissionState.value) {
                        showNotificationRationale.value = true
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.exact_alarm_permission_required),
                            Toast.LENGTH_LONG
                        ).show()
                        showExactAlarmRationale.value = true
                        openAppSettings(context)
                    }
                    return@PrimaryButton
                }
                viewModel.onEvent(TripEvents.SaveTrip)
            },
            text = stringResource(id = R.string.add)
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@SuppressLint("NewApi")
@Composable
private fun checkForExactAlarmPermission(
    navigateUp: () -> Unit,
): Pair<MutableState<Boolean>, MutableState<Boolean>> {
    val context = LocalContext.current
    val permissionState = remember { mutableStateOf(false) }
    val shouldShowRationalDialog = remember { mutableStateOf(false) }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val requestPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            // After returning from settings, recheck the permission
            permissionState.value = alarmManager?.canScheduleExactAlarms() == true
        }

        LaunchedEffect(Unit) {
            if (alarmManager?.canScheduleExactAlarms() == true) {
                permissionState.value = true
            } else {
                shouldShowRationalDialog.value = true
            }
        }

        if (shouldShowRationalDialog.value) {
            ShowPermissionRationaleDialog(
                onRequestPermission = {
                    // Open app settings for exact alarm permission
                    val settingsIntent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        .apply { data = Uri.fromParts("package", context.packageName, null) }
                    requestPermissionLauncher.launch(settingsIntent)
                    shouldShowRationalDialog.value = false
                },
                onDismissRequest = {
                    shouldShowRationalDialog.value = false
                    navigateUp()
                }
            )
        }
    } else {
        // For devices below API 31, exact alarm permission is not required
        permissionState.value = true
    }

    return Pair(permissionState, shouldShowRationalDialog)
}

private fun openAppSettings(context: android.content.Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

@Composable
private fun checkForNotificationPermission(
    navigateUp: () -> Unit,
): Pair<MutableState<Boolean>, MutableState<Boolean>> {
    val context = LocalContext.current
    val permissionState = remember { mutableStateOf(false) }
    val shouldShowRationalDialog = remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            permissionState.value = true
            Toast.makeText(
                context,
                context.getString(R.string.notification_permission_granted),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.notification_permission_is_required_for_reminders),
                Toast.LENGTH_LONG
            ).show()

            if (!shouldShowRationalDialog.value) {
                val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .apply { data = Uri.fromParts("package", context.packageName, null) }

                context.startActivity(settingsIntent)
            }
        }
    }

    LaunchedEffect(Unit) {
        if (hasPostNotificationPermission(context)) {
            permissionState.value = true
        } else {
            if (shouldShowPostNotificationRequestPermissionRationale(context)) {
                shouldShowRationalDialog.value = true
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    if (shouldShowRationalDialog.value) {
        ShowPermissionRationaleDialog(
            onRequestPermission = {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                shouldShowRationalDialog.value = false
            },
            onDismissRequest = {
                shouldShowRationalDialog.value = false
                navigateUp()
            }
        )

    }
    return Pair(permissionState, shouldShowRationalDialog)
}

@Composable
fun ShowPermissionRationaleDialog(
    onRequestPermission: () -> Unit,
    onDismissRequest : () -> Unit
) {
    AlertDialog(
        onDismissRequest = {onDismissRequest()},
        title = {
            Text(stringResource(R.string.notification_permission_required))
        },
        text = {
            Text(stringResource(R.string.this_app_requires_notification_permissions_to_remind_you_about_your_trips))
        },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = {
                    onRequestPermission()
                }
            ) {
                Text(stringResource(R.string.allow))
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = { onDismissRequest() }
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}


@Composable
@Preview
fun PreviewAddTripScreen(){
    TravelAppTheme {
       AddTripScreen()
    }
}