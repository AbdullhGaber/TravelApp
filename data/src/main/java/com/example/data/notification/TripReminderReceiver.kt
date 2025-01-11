package com.example.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.domain.repositories.trip.NotificationHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TripReminderReceiver: BroadcastReceiver(){
    @Inject
    lateinit var mTripNotificationHandler: NotificationHandler
    override fun onReceive(context: Context?, intent: Intent?) {
        val tripName = intent?.getStringExtra("tripName") ?: "Your Trip"
        mTripNotificationHandler.showTripReminderNotification(tripName)
    }
}