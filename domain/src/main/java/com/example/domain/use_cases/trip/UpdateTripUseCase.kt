package com.example.domain.use_cases.trip

import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripRepository
import javax.inject.Inject

class UpdateTripUseCase @Inject constructor(
    private val mTripRepository : TripRepository
){
    operator fun invoke(
        trip : TripEntity,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        mTripRepository.updateTrip(trip,onSuccess,onFailure)
    }
}