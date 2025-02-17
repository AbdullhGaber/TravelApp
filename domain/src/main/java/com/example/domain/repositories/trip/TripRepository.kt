package com.example.domain.repositories.trip

import com.example.domain.entity.TripEntity
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    fun getTrips(
        uid : String,
        onSuccess : (List<TripEntity>?) -> Unit,
        onFailure : (Throwable) -> Unit
    )

    fun addTrip(
        trip: TripEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun getTripById(
        id : String,
        uid:String,
        onSuccess: (TripEntity) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun getScheduledTrips() : Flow<List<TripEntity>>
}

interface TripRemoteDataSource{
    fun getTrips(
        uid : String,
        onSuccess : (List<TripEntity>?) -> Unit,
        onFailure : (Throwable) -> Unit
    )

    fun addTrip(
        trip: TripEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun getTripById(
        id : String,
        uid: String,
        onSuccess: (TripEntity) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}

interface TripOfflineDataSource{
    fun getTrips() : Flow<List<TripEntity>>

    suspend fun addTrip(trip: TripEntity)

    fun getScheduledTrips() : Flow<List<TripEntity>>
}

fun interface TripNotificationScheduler {
    fun schedule(trip: TripEntity)
}

fun interface NotificationHandler {
    fun showTripReminderNotification(
        tripName: String,
        tripStartDes: String,
        tripEndDes: String,
    )
}
