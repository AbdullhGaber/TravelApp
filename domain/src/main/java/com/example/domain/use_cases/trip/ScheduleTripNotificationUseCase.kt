package com.example.domain.use_cases.trip

import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripNotificationScheduler

class ScheduleTripNotificationUseCase(
    private val mScheduleTripNotificationScheduler : TripNotificationScheduler
){
    operator fun invoke(trip : TripEntity){
        mScheduleTripNotificationScheduler.schedule(trip)
    }
}