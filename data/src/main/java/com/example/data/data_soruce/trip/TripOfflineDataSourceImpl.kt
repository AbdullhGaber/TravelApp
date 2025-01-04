package com.example.data.data_soruce.trip

import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripOfflineDataSource
import javax.inject.Inject

class TripOfflineDataSourceImpl @Inject constructor(

): TripOfflineDataSource {
    override fun getTrips(): List<TripEntity>? {
        TODO("Not yet implemented")
    }
}