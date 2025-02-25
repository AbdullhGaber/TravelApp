package com.example.domain.use_cases.trip

import com.example.domain.repositories.trip.TripRepository

class DeleteTripUseCase(
    private val mTripRepository: TripRepository
){
    operator fun invoke(
        tripId : String,
        uid:String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ){
        mTripRepository.deleteTrip(tripId, uid, onSuccess, onFailure)
    }
}