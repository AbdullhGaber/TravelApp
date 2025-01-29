package com.example.data.repositories.trip

import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripOfflineDataSource
import com.example.domain.repositories.trip.TripRemoteDataSource
import com.example.domain.repositories.trip.TripRepository
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val mTripRemoteDataSource: TripRemoteDataSource,
    private val mTripOfflineDataSource: TripOfflineDataSource
) : TripRepository {
    override fun getTrips(
        uid : String,
        onSuccess : (List<TripEntity>?) -> Unit,
        onFailure : (Throwable) -> Unit
    ) {
         mTripRemoteDataSource.getTrips(uid, onSuccess, onFailure)
    }

    override fun addTrip(
        uid: String,
        trip: TripEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        mTripRemoteDataSource.addTrip(uid, trip, onSuccess, onFailure)
    }
}