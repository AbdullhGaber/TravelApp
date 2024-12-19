package com.example.travelapp.screens.nav_graph

sealed class Route(val route : String) {
    data object AuthNavigation : Route("authNavigation")
    data object RegisterScreen : Route("registerScreen")
    data object LoginScreen : Route("loginScreen")

    data object UpComingScreen : Route("upcomingScreen")
}