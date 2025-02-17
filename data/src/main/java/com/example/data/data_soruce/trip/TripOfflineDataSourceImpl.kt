package com.example.data.data_soruce.trip

import com.example.data.database.TripDao
import com.example.data.mapper.toEntity
import com.example.data.mapper.toModel
import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripOfflineDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
}