package com.example.domain.use_cases.trip

import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripRepository
import javax.inject.Inject

class GetTripByIdUseCase @Inject constructor(
    private val mTripRepository: TripRepository
) {
    operator fun invoke(
        id : String,
        uid : String,
        onSuccess : (TripEntity) -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        mTripRepository.getTripById(id, uid, onSuccess, onFailure)
    }
}