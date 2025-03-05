package com.example.data.notification


import com.example.domain.repositories.trip.TripNotificationScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {
    @Binds
    abstract fun provideTripNotificationScheduler(tripNotificationSchedulerImpl: TripNotificationSchedulerImpl) : TripNotificationScheduler
}