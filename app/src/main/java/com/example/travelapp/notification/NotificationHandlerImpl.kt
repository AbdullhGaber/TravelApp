package com.example.travelapp.notification

import android.content.Context
import android.content.Intent
import com.example.data.uitls.Constants.SHOW_TRIP_REMINDER_KEY
import com.example.data.uitls.Constants.TRIP_END_DESTINATION_KEY
import com.example.data.uitls.Constants.TRIP_ID_KEY
import com.example.data.uitls.Constants.TRIP_NAME_KEY
import com.example.data.uitls.Constants.TRIP_START_DESTINATION_KEY
import com.example.domain.repositories.trip.NotificationHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class NotificationHandlerImpl  @Inject constructor(
    @ApplicationContext val mContext : Context
) : NotificationHandler {
    override fun startService(
        tripId: String,
        tripName: String,
        tripStartDes: String,
        tripEndDes: String,
    ){
        val serviceIntent = Intent(mContext, TripReminderForegroundService::class.java).apply {
            putExtra(SHOW_TRIP_REMINDER_KEY, true)
            putExtra(TRIP_ID_KEY,tripId)
            putExtra(TRIP_NAME_KEY,tripName)
            putExtra(TRIP_START_DESTINATION_KEY,tripStartDes)
            putExtra(TRIP_END_DESTINATION_KEY,tripEndDes)
        }

        mContext.startForegroundService(serviceIntent)
    }
}