package com.example.travelapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.example.data.uitls.Constants.TRIP_END_DESTINATION_KEY
import com.example.data.uitls.Constants.TRIP_ID_KEY
import com.example.data.uitls.Constants.TRIP_NAME_KEY
import com.example.data.uitls.Constants.TRIP_START_DESTINATION_KEY
import com.example.travelapp.R
import com.example.travelapp.MainActivity


class TripReminderForegroundService : Service() {

    companion object {
        const val CHANNEL_ID = "trip_reminder_channel"
        const val NOTIFICATION_ID = 101
        const val STOP_ACTION = "com.example.travelapp.STOP_REMINDER"
    }


    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var mAudioManager: AudioManager
    private lateinit var mFocusRequest: AudioFocusRequest

    override fun onCreate() {
        super.onCreate()
        mAudioManager = getSystemService(AudioManager::class.java)

        mFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .setOnAudioFocusChangeListener { focusChange ->
                when (focusChange) {
                    AudioManager.AUDIOFOCUS_LOSS -> stopReminder()
                }
            }.build()

        val result = mAudioManager.requestAudioFocus(mFocusRequest)
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            stopReminder()
        }

        mMediaPlayer = MediaPlayer().apply {
            setDataSource(this@TripReminderForegroundService,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            isLooping = true
            prepare()
        }

        createNotificationChannel()
    }

    private fun stopReminder(){
        try {
            if (::mMediaPlayer.isInitialized && mMediaPlayer.isPlaying) {
                mMediaPlayer.stop()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } finally {
            if (::mMediaPlayer.isInitialized) {
                mMediaPlayer.release()
            }

            mAudioManager.abandonAudioFocusRequest(mFocusRequest)
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer.release()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val tripId = intent?.getStringExtra(TRIP_ID_KEY) ?: "Your Trip"
        val tripName = intent?.getStringExtra(TRIP_NAME_KEY) ?: "Your Trip"
        val tripStartDes = intent?.getStringExtra(TRIP_START_DESTINATION_KEY) ?: "Start"
        val tripEndDes = intent?.getStringExtra(TRIP_END_DESTINATION_KEY) ?: "End"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            startForeground(NOTIFICATION_ID, createNotification(tripName, tripStartDes, tripEndDes), ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK )
        }else{
            startForeground(NOTIFICATION_ID, createNotification(tripName, tripStartDes, tripEndDes))
        }

        mMediaPlayer.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(
        tripName: String,
        tripStartDes: String,
        tripEndDes: String
    ): Notification {
        val stopIntent = Intent(this,StopReminderReceiver::class.java).apply {
            action = STOP_ACTION
        }

        val stopPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT  or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)

        val notification = notificationBuilder
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.trip_reminder))
            .setContentText(getString(R.string.it_s_time_for_your_trip) + tripName + ": $tripStartDes to $tripEndDes")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setOnlyAlertOnce(false)
            .setOngoing(true)
            .addAction(R.drawable.ic_launcher_foreground, "Stop Reminder", stopPendingIntent)
            .setVibrate(longArrayOf(0, 500, 1000)) // Vibration pattern
            .setDefaults(NotificationCompat.DEFAULT_LIGHTS or NotificationCompat.DEFAULT_VIBRATE)
            .build()

        return notification
    }

    private fun createNotificationChannel(){
        val channelName = "Trip Reminders"
        val channelDescription = "Notifications to remind you about your trips"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
            description = channelDescription
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 500, 1000)  // Vibration pattern
            setSound(
                Settings.System.DEFAULT_ALARM_ALERT_URI, AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            )  // Set default alarm sound
        }

        val notificationManager = getSystemService(NotificationManager::class.java)

        notificationManager.createNotificationChannel(channel)
    }
}