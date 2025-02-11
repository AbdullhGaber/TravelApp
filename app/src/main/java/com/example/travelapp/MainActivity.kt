package com.example.travelapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.data.uitls.Constants.SHOW_TRIP_REMINDER_KEY
import com.example.data.uitls.Constants.TRIP_END_DESTINATION_KEY
import com.example.data.uitls.Constants.TRIP_NAME_KEY
import com.example.data.uitls.Constants.TRIP_START_DESTINATION_KEY
import com.example.travelapp.screens.nav_graph.NavGraph
import com.example.travelapp.screens.upcoming.UpcomingViewModel
import com.example.travelapp.ui.theme.TravelAppTheme
import com.example.travelapp.utils.hasPostNotificationPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var navController : NavHostController? = null
    val viewModel : MainViewModel by viewModels()
    private val requestNotificationPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this,
                    getString(R.string.notification_permission_granted), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,
                    getString(R.string.notification_permission_is_required_for_reminders), Toast.LENGTH_LONG).show()
            }
        }

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if(!hasPostNotificationPermission(this))
            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        if(intent != null){
            this.onNewIntent(intent)
        }

        setContent {
            TravelAppTheme {
                navController = rememberNavController()
                NavGraph(
                    navController = navController!!,
                    mainViewModel = viewModel,
                    startDestination = viewModel.startDestination.value
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val shouldShowDialog = intent.getBooleanExtra(SHOW_TRIP_REMINDER_KEY, false)
        val tripName = intent.getStringExtra(TRIP_NAME_KEY) ?: "My Trip"
        val tripStartDes = intent.getStringExtra(TRIP_START_DESTINATION_KEY) ?: "My Start Des"
        val tripEndDes = intent.getStringExtra(TRIP_END_DESTINATION_KEY) ?: "My Start Des"
        if (shouldShowDialog) {
            viewModel.showTripReminderDialog()
            viewModel.setTripData(tripName,tripStartDes,tripEndDes)
        }
    }

}
