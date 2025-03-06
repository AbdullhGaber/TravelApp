package com.example.domain.repositories.trip

import androidx.annotation.IntRange
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

    suspend fun updateTripHasTimeCome(id: String, @IntRange(0,1) value : Int)

    fun deleteTrip(
        tripId : String,
        uid:String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun updateTrip(
        trip: TripEntity,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    )
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

     fun deleteTrip(
        tripId : String,
        uid: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun updateTrip(
        trip: TripEntity,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    )
}

interface TripOfflineDataSource{
    fun getTrips() : Flow<List<TripEntity>>

    suspend fun addTrip(trip: TripEntity)

    fun getScheduledTrips() : Flow<List<TripEntity>>

    suspend fun updateTripHasTimeCome(id: String, @IntRange(0,1) value: Int)

    suspend fun deleteTrip(
        tripId : String,
        uid: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )

    suspend fun getTripById(
        id : String,
        onSuccess: (TripEntity) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    suspend fun updateTrip(
        trip: TripEntity,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    )
}

interface TripNotificationScheduler {
    fun schedule(trip: TripEntity)
    fun cancelTripSchedule(trip: TripEntity)
}

fun interface NotificationHandler {
    fun startService(
        tripId: String,
        tripType: String,
        tripName: String,
        tripStartDes: String,
        tripEndDes: String,
    )
}
