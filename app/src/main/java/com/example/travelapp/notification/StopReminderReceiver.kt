package com.example.travelapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StopReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == TripReminderForegroundService.STOP_ACTION) {
            Log.e("StopReminderReceiver", "Stop action received")
            val stopIntent = Intent(context, TripReminderForegroundService::class.java)
            context?.stopService(stopIntent)
        }
    }
}