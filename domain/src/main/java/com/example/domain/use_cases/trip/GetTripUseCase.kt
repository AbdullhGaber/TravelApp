package com.example.domain.use_cases.trip

import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripRepository
import javax.inject.Inject

class GetTripUseCase @Inject constructor(
    private val mTripRepository: TripRepository
) {
    operator fun invoke(
        uid : String,
        onSuccess : (List<TripEntity>?) -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        mTripRepository.getTrips(uid, onSuccess, onFailure)
    }
}