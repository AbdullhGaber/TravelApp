package com.example.data.data_soruce.trip

import android.util.Log
import com.example.domain.entity.TripEntity
import com.example.domain.entity.TripUserEntity
import com.example.domain.repositories.trip.TripRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class TripRemoteDataSourceImpl @Inject constructor(
    private val mFireStore : FirebaseFirestore
) : TripRemoteDataSource {
    override fun getTrips(
        uid : String,
        onSuccess : (List<TripEntity>?) -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        Log.e("Test FireStore","UID = $uid")
        val tripRefSnapshot = mFireStore.collection(TripUserEntity.USER_COLLECTION).document(uid).collection(TripEntity.TRIP_COLLECTION).get()

        tripRefSnapshot.addOnSuccessListener { result ->
            val trips = result.toObjects(TripEntity::class.java)
            Log.e("FIB FireStore data source" , "trips retrieved")
            onSuccess(trips)
        }.addOnFailureListener {
            onFailure(it)
            Log.e("FIB FireStore data store" , "Error : ${it.message}")
        }
    }
}