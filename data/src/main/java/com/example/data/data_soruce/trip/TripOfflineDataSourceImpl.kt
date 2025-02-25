package com.example.data.data_soruce.trip

import android.util.Log
import androidx.annotation.IntRange
import com.example.data.database.TripDao
import com.example.data.mapper.toEntity
import com.example.data.mapper.toModel
import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripOfflineDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TripOfflineDataSourceImpl @Inject constructor(
    private val mTripDao : TripDao
): TripOfflineDataSource {
    override fun getTrips(): Flow<List<TripEntity>> {
          return mTripDao.getAllTrips().map{ it.map { it.toEntity() } }
    }

    override suspend fun addTrip(trip: TripEntity, ) {
        mTripDao.addTrip(trip.toModel())
    }

    override fun getScheduledTrips(): Flow<List<TripEntity>> {
        return mTripDao.getScheduledTrips().map{ it.map { it.toEntity() } }
    }

    override suspend fun updateTripHasTimeCome(id: String,@IntRange(0,1) value: Int) {
        mTripDao.updateHasTimeCome(id, value)
    }

    override suspend fun deleteTrip(
        tripId: String,
        uid: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        try {
            mTripDao.deleteTrip(tripId)
            Log.e("Trip Offline Data Source" , "Trip deleted successfully")
            onSuccess()
        }catch (e : Exception){
            onFailure(e)
            Log.e("Trip Offline Data Source" , "Error : ${e.message}")
        }
    }
}