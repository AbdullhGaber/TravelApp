package com.example.domain.use_cases.trip

import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripNotificationScheduler
import javax.inject.Inject

class RescheduleTripNotificationUseCase @Inject constructor(
    private val mScheduler: TripNotificationScheduler
) {
    operator fun invoke(trip : TripEntity, snoozeTime : Long = 0L, isFirstTrip : Boolean = true){
        mScheduler.reschedule(trip, snoozeTime, isFirstTrip)
    }
}