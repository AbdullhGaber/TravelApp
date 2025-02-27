package com.example.domain.use_cases.trip

import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripRepository
import kotlinx.coroutines.flow.Flow

class GetScheduledTrips(
    private val mTripRepository: TripRepository
){
    operator fun invoke() : Flow<List<TripEntity>>{
        return mTripRepository.getScheduledTrips()
    }
}