package com.example.travelapp.notification


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.data.uitls.mContext
import com.example.domain.repositories.trip.NotificationHandler
import com.example.travelapp.MainActivity
import com.example.travelapp.R
import com.example.travelapp.utils.hasPostNotificationPermission


class NotificationHandlerImpl : NotificationHandler {
    @SuppressLint("MissingPermission")
    override fun showTripReminderNotification(tripName: String) {
        val intent = Intent(mContext,MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            mContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        createNotificationChannel(mContext)

        val notificationBuilder = NotificationCompat.Builder(mContext,"trip_reminder_channel")
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val notification = notificationBuilder
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(mContext.getString(R.string.trip_reminder))
            .setContentText(mContext.getString(R.string.it_s_time_for_your_trip) + tripName)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(soundUri)  // Use the system's default alarm sound
            .setVibrate(longArrayOf(0, 500, 1000)) // Vibration pattern
            .setDefaults(NotificationCompat.DEFAULT_LIGHTS or NotificationCompat.DEFAULT_VIBRATE)
            .build()


        if (hasPostNotificationPermission(mContext)) {
            NotificationManagerCompat.from(mContext).notify(System.currentTimeMillis().toInt(), notification)
        }
    }

    private fun createNotificationChannel(context: Context) {
        val channelId = "trip_reminder_channel"
        val channelName = "Trip Reminders"
        val channelDescription = "Notifications to remind you about your trips"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 500, 1000)  // Vibration pattern
            setSound(Settings.System.DEFAULT_ALARM_ALERT_URI, AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            )  // Set default alarm sound
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)

        notificationManager.createNotificationChannel(channel)
    }
}