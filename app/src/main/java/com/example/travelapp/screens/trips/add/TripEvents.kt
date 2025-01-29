package com.example.travelapp.screens.trips.add

sealed class TripEvents {
    data object SaveTrip : TripEvents()
}