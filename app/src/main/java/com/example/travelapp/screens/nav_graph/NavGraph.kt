package com.example.travelapp.screens.nav_graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.travelapp.screens.login.LoginScreen
import com.example.travelapp.screens.navigator.TripNavigator
import com.example.travelapp.screens.register.RegisterScreen

@Composable
fun NavGraph(
    startDestination : String,
    navController : NavHostController,
){
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
                   navigateToSignIn = {
                       navController.navigate(Route.LoginScreen.route)
                   }
               )
            }

            composable(
                route = Route.LoginScreen.route
            ){
                LoginScreen(
                    viewModel = hiltViewModel(),
                    navigateToSignUp = {
                        navController.navigate(Route.RegisterScreen.route)
                    }
                )
            }
        }

        composable(
            route = Route.HomeNavigation.route
        ){
            TripNavigator()
        }
    }
}


