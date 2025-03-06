package com.example.domain.use_cases.trip

import androidx.annotation.IntRange
import com.example.domain.repositories.trip.TripRepository
import javax.inject.Inject

class TripReminderCancelUseCase @Inject constructor(
    private val mTripRepository: TripRepository
) {
    suspend operator fun invoke(id : String, @IntRange(0,1) value : Int){
        mTripRepository.updateTripHasTimeCome(id,value)
    }
}