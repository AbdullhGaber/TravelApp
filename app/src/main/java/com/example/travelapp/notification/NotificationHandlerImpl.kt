package com.example.travelapp.notification

import android.content.Context
import android.content.Intent
import com.example.data.database.TripDao
import com.example.data.uitls.Constants.SHOW_TRIP_REMINDER_KEY
import com.example.data.uitls.Constants.TRIP_END_DESTINATION_KEY
import com.example.data.uitls.Constants.TRIP_ID_KEY
import com.example.data.uitls.Constants.TRIP_NAME_KEY
import com.example.data.uitls.Constants.TRIP_START_DESTINATION_KEY
import com.example.data.uitls.Constants.TRIP_TYPE_KEY
import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.NotificationHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class NotificationHandlerImpl  @Inject constructor(
    @ApplicationContext val mContext : Context,
    private val mTripDao: TripDao
) : NotificationHandler {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private fun updateHasTimeCome(tripId: String, tripType:String){
        coroutineScope.launch {
            if(tripType == TripEntity.ONE_DIRECTION_TRIP ){
                mTripDao.updateHasFirstTripTimeCome(tripId,1)
            }else{
                mTripDao.updateHasSecondTripTimeCome(tripId,1)
            }
        }
    }
    override fun startService(
        tripId: String,
        tripType: String,
        tripName: String,
        tripStartDes: String,
        tripEndDes: String,
    ){
        updateHasTimeCome(tripId,tripType)
        val serviceIntent = Intent(mContext, TripReminderForegroundService::class.java).apply {
            putExtra(SHOW_TRIP_REMINDER_KEY, true)
            putExtra(TRIP_ID_KEY,tripId)
            putExtra(TRIP_TYPE_KEY,tripType)
            putExtra(TRIP_NAME_KEY,tripName)
            putExtra(TRIP_START_DESTINATION_KEY,tripStartDes)
            putExtra(TRIP_END_DESTINATION_KEY,tripEndDes)
        }

        mContext.startForegroundService(serviceIntent)
    }
}