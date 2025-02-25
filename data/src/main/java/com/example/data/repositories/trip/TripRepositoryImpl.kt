package com.example.data.repositories.trip

import android.content.Context
import androidx.annotation.IntRange
import com.example.data.uitls.NetworkUtil
import com.example.domain.entity.TripEntity
import com.example.domain.repositories.trip.TripOfflineDataSource
import com.example.domain.repositories.trip.TripRemoteDataSource
import com.example.domain.repositories.trip.TripRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val mTripRemoteDataSource: TripRemoteDataSource,
    private val mTripOfflineDataSource: TripOfflineDataSource,
    @ApplicationContext private val mContext: Context
) : TripRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    override fun getTrips(
        uid : String,
        onSuccess : (List<TripEntity>?) -> Unit,
        onFailure : (Throwable) -> Unit
    ) {
        if(NetworkUtil.isDeviceConnected(mContext)){
            mTripRemoteDataSource.getTrips(
                uid = uid,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }else {
            coroutineScope.launch {
                mTripOfflineDataSource.getTrips().collect {
                    onSuccess(it)
                }
            }
        }
    }

    override fun addTrip(
        trip: TripEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        mTripRemoteDataSource.addTrip(trip, onSuccess = {
            coroutineScope.launch {
                mTripOfflineDataSource.addTrip(trip)
                onSuccess()
            }
        }, onFailure)
    }

    override fun getTripById(
        id: String,
        uid: String,
        onSuccess: (TripEntity) -> Unit,
        onFailure: (Throwable) -> Unit,
    ){
        mTripRemoteDataSource.getTripById(id, uid, onSuccess, onFailure)
    }

    override fun getScheduledTrips(): Flow<List<TripEntity>> {
        return mTripOfflineDataSource.getScheduledTrips()
    }

    override suspend fun updateTripHasTimeCome(id: String, @IntRange(0,1) value : Int){
        mTripOfflineDataSource.updateTripHasTimeCome(id, value)
    }

    override fun deleteTrip(
        tripId : String,
        uid:String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        mTripRemoteDataSource.deleteTrip(
            tripId = tripId,
            uid = uid,
            onSuccess = {
                onSuccess()
                coroutineScope.launch {
                    mTripOfflineDataSource.deleteTrip(tripId, uid, onSuccess, onFailure)
                }
            },
            onFailure = onFailure
        )
    }
}