package com.example.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.data.uitls.Constants.TRIP_END_DESTINATION_KEY
import com.example.data.uitls.Constants.TRIP_ID_KEY
import com.example.data.uitls.Constants.TRIP_NAME_KEY
import com.example.data.uitls.Constants.TRIP_START_DESTINATION_KEY
import com.example.data.uitls.Constants.TRIP_TYPE_KEY
import com.example.domain.repositories.trip.NotificationHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TripReminderReceiver: BroadcastReceiver(){
    @Inject
    lateinit var mTripNotificationHandler: NotificationHandler

    override fun onReceive(context: Context?, intent: Intent?) {
        val tripId = intent?.getStringExtra(TRIP_ID_KEY) ?: "trip id"
        val tripType = intent?.getStringExtra(TRIP_TYPE_KEY) ?: "trip type"
        val tripName = intent?.getStringExtra(TRIP_NAME_KEY) ?: "Your Trip"
        val tripStartDes = intent?.getStringExtra(TRIP_START_DESTINATION_KEY) ?: "Your Start Destination"
        val tripEndDes = intent?.getStringExtra(TRIP_END_DESTINATION_KEY) ?: "Your End Destination"
        mTripNotificationHandler.startService(tripId,tripType,tripName,tripStartDes,tripEndDes)
    }
}