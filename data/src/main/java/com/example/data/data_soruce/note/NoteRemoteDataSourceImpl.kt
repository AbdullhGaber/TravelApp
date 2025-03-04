package com.example.data.data_soruce.note

import android.util.Log
import com.example.domain.entity.NoteEntity
import com.example.domain.entity.TripEntity
import com.example.domain.entity.TripEntity.Companion.TRIP_COLLECTION
import com.example.domain.entity.TripUserEntity.Companion.USER_COLLECTION
import com.example.domain.repositories.note.NoteRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class NoteRemoteDataSourceImpl @Inject constructor(
    private val mFirebaseFireStore: FirebaseFirestore
): NoteRemoteDataSource {
    override fun addNote(note: NoteEntity, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val tripDocRef = mFirebaseFireStore
            .collection(USER_COLLECTION)
            .document(note.uid)
            .collection(TRIP_COLLECTION)
            .document(note.tripId)

        var notes : List<NoteEntity>

        tripDocRef.get().addOnSuccessListener {  result ->
            notes = result?.toObject(TripEntity::class.java)?.notes!!
            notes = notes.toMutableList().apply { add(note) }

            tripDocRef.update("notes", notes).addOnSuccessListener {
                Log.e("FIB Fire store", "note added successfully")
                onSuccess()
            }.addOnFailureListener {
                Log.e("FIB Fire store", "Error : ${it.message}")
                onFailure(it)
            }

        }.addOnFailureListener {
            Log.e("FIB Fire store", "Error : ${it.message}")
            onFailure(it)
        }
    }

    override fun getNotes(
        uid: String,
        tripId: String,
        onSuccess: (List<NoteEntity>) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        val tripDocRef = mFirebaseFireStore
            .collection(USER_COLLECTION)
            .document(uid)
            .collection(TRIP_COLLECTION)
            .document(tripId)

        tripDocRef.addSnapshotListener { result ,error ->
            if(error != null){
                onFailure(error)
                Log.e("FIB FireStore" , "Error : ${error.message}")
            }
            val trip = result?.toObject(TripEntity::class.java)
            onSuccess(trip?.notes?: emptyList())
        }
    }
}