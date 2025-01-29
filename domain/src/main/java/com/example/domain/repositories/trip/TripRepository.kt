package com.example.domain.repositories.trip

import com.example.domain.entity.TripEntity

interface TripRepository {
    fun getTrips(
        uid : String,
        onSuccess : (List<TripEntity>?) -> Unit,
        onFailure : (Throwable) -> Unit
    )

    fun addTrip(
        uid: String,
        trip: TripEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )
}

interface TripRemoteDataSource{
    fun getTrips(
        uid : String,
        onSuccess : (List<TripEntity>?) -> Unit,
        onFailure : (Throwable) -> Unit
    )

    fun addTrip(
        uid: String,
        trip: TripEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )
}

interface TripOfflineDataSource{
    fun getTrips() : List<TripEntity>?
}

fun interface TripNotificationScheduler {
    fun schedule(trip: TripEntity)
}

fun interface NotificationHandler {
    fun showTripReminderNotification(tripName: String)
}
