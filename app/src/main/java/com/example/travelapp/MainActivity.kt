package com.example.travelapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelapp.screens.nav_graph.NavGraph
import com.example.travelapp.ui.theme.TravelAppTheme
import com.example.travelapp.utils.hasPostNotificationPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
        setContent {
            TravelAppTheme {
                val viewModel : MainViewModel = hiltViewModel()
                NavGraph(startDestination = viewModel.startDestination.value)
            }
        }
    }
}
