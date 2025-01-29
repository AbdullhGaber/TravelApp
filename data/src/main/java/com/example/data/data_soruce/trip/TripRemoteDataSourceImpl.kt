package com.example.data.data_soruce.trip

import android.util.Log
import com.example.domain.entity.TripEntity
import com.example.domain.entity.TripUserEntity
import com.example.domain.entity.TripUserEntity.Companion.USER_COLLECTION
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
        val tripRefSnapshot = mFireStore.collection(USER_COLLECTION).document(uid).collection(TripEntity.TRIP_COLLECTION).get()

        tripRefSnapshot.addOnSuccessListener { result ->
            val trips = result.toObjects(TripEntity::class.java)
            Log.e("FIB FireStore data source" , "trips retrieved")
            onSuccess(trips)
        }.addOnFailureListener {
            onFailure(it)
            Log.e("FIB FireStore data store" , "Error : ${it.message}")
        }
    }

    override fun addTrip(
        uid: String,
        trip: TripEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        val userDocRef = mFireStore.collection(USER_COLLECTION).document(uid)
        val tripCollection = userDocRef.collection(TripEntity.TRIP_COLLECTION).document()
        trip.id = tripCollection.id
        tripCollection.set(trip).addOnSuccessListener {
            Log.e("FIB FireStore Repo","trip saved successfully")
            onSuccess()
        }.addOnFailureListener {
            Log.e("FIB FireStore Repo" , "Error : ${it.message}")
            onFailure(it)
        }
    }
}