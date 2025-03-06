package com.example.travelapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.travelapp.screens.nav_graph.NavGraph
import com.example.travelapp.ui.theme.TravelAppTheme
import com.example.travelapp.utils.hasPostNotificationPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var navController : NavHostController? = null
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

        setContent {
            TravelAppTheme {
                installSplashScreen().setKeepOnScreenCondition{viewModel.shouldSplashScreenOn.value}
                navController = rememberNavController()
                NavGraph(
                    navController = navController!!,
                    startDestination = viewModel.startDestination.value
                )
            }
        }
    }
}
