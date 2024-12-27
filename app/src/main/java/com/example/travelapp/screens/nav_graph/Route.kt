package com.example.travelapp.screens.nav_graph

sealed class Route(val route : String) {
    data object AuthNavigation : Route("authNavigation")
    data object RegisterScreen : Route("registerScreen")
    data object LoginScreen : Route("loginScreen")

    data object HomeNavigation : Route("homeNavigation")
    data object UpComingScreen : Route("upcomingScreen")
    data object HistoryScreen : Route("historyScreen")
    data object HistoryMapScreen : Route("historyMapScreen")
    data object AddTripScreen : Route("addTripScreen")
    data object EditTripScreen : Route("addTripScreen")

}