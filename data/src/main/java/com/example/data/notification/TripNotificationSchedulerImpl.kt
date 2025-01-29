package com.example.data.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.data.uitls.formatTimeDate
import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripNotificationScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TripNotificationSchedulerImpl @Inject constructor(
    @ApplicationContext private val mContext : Context
): TripNotificationScheduler {
    override fun schedule(trip: TripEntity) {
        val alarmManager = mContext.getSystemService(AlarmManager::class.java)

        val intent = Intent(mContext, TripReminderReceiver::class.java)
            .apply { putExtra("tripName" , trip.name) }

        val pendingIntent = PendingIntent.getBroadcast(
            mContext,
          0 ,
           intent,
           PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = formatTimeDate(time = trip.time, date = trip.date)?.time

        triggerTime?.let{
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                it,
                pendingIntent
            )
        }

    }
}