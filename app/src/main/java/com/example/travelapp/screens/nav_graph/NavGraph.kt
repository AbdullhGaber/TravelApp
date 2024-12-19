package com.example.travelapp.screens.nav_graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.travelapp.screens.login.LoginScreen
import com.example.travelapp.screens.register.RegisterScreen

@Composable
fun NavGraph(
    startDestination : String
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination){
        navigation(
            route = Route.AuthNavigation.route,
            startDestination = Route.LoginScreen.route
        ){
            composable(
                route = Route.RegisterScreen.route
            ){
               RegisterScreen(
                   viewModel = hiltViewModel(),
                   navigateToHome = {
//                       navController.navigate(Route.UpComingScreen.route) {
//                           popUpTo(Route.RegisterScreen.route) { inclusive = true } // Clears the back stack
//                       }
                   },
               )
            }

            composable(
                route = Route.LoginScreen.route
            ){
                LoginScreen(
                    viewModel = hiltViewModel(),
                    navigateToHome = {
//                       navController.navigate(Route.UpComingScreen.route) {
//                           popUpTo(Route.RegisterScreen.route) { inclusive = true } // Clears the back stack
//                       }
                    },
                    navigateToSignUp = {
                        navController.navigate(Route.RegisterScreen.route)
                    }
                )
            }
        }
    }
}


