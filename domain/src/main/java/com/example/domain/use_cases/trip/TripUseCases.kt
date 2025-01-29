package com.example.domain.use_cases.trip

data class TripUseCases(
    val getTripUseCase : GetTripUseCase,
    val scheduleTripNotificationUseCase: ScheduleTripNotificationUseCase,
    val addTripUseCase: AddTripUseCase
)
