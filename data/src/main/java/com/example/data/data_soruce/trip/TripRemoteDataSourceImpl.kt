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
        val tripRefSnapshot = mFireStore.collection(USER_COLLECTION).document(uid).collection(TripEntity.TRIP_COLLECTION)

        tripRefSnapshot.addSnapshotListener { snapShot , error ->
            if(error != null){
                onFailure(error)
                Log.e("FIB FireStore data store" , "Error : ${error.message}")
            }

            if(snapShot != null && !snapShot.isEmpty){
                val trips = snapShot.toObjects(TripEntity::class.java)
                Log.e("FIB FireStore data source" , "trips retrieved")
                onSuccess(trips)
            }else{
                Log.e("FIB FireStore data source", "No trips found")
                onSuccess(emptyList())
            }
        }
    }

    override fun addTrip(
        trip: TripEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        val userDocRef = mFireStore.collection(USER_COLLECTION).document(trip.uid)
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

    override fun getTripById(
        id: String,
        uid:String,
        onSuccess: (TripEntity) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        val tripDocRef = mFireStore.
        collection(USER_COLLECTION).
        document(uid).
        collection(TripEntity.TRIP_COLLECTION).document(id)

        tripDocRef.get().addOnSuccessListener {
            val trip = it.toObject(TripEntity::class.java)
            if(trip != null){
                onSuccess(trip)
                Log.e("FIB FireStore Repo","trip retrieved by id successfully")
            }else{
                Log.e("FIB FireStore Repo","Error : trip is null")
            }
        }.addOnFailureListener {
            onFailure(it)
            Log.e("FIB FireStore Repo","Error : ${it.message}")
        }
    }

    override fun deleteTrip(
        tripId: String,
        uid: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        val tripDocRef = mFireStore.
        collection(USER_COLLECTION).
        document(uid).
        collection(TripEntity.TRIP_COLLECTION).document(tripId)

        tripDocRef.delete().addOnSuccessListener {
            onSuccess()
            Log.e("FIB Firestore Repo","trip deleted successfully")
        }.addOnFailureListener {
            onFailure(it)
            Log.e("FIB Firestore Repo","Error : ${it.message}")
        }
    }

    override fun updateTrip(
        trip: TripEntity,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        val tripDocRef = mFireStore.
        collection(USER_COLLECTION).
        document(trip.uid).
        collection(TripEntity.TRIP_COLLECTION).document(trip.id!!)

        tripDocRef.set(trip).addOnSuccessListener {
            onSuccess()
            Log.e("FIB Firestore Repo","trip updated successfully")
        }.addOnFailureListener {
            onFailure(it)
            Log.e("FIB Firestore Repo","Error : ${it.message}")
        }
    }
}