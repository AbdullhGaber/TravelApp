package com.example.travelapp.notification

import com.example.domain.repositories.trip.NotificationHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {
    @Binds
    abstract fun provideNotificationHandler(notificationHandlerImpl: NotificationHandlerImpl) : NotificationHandler
}