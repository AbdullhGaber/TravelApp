package com.example.data.data_soruce.note

import android.util.Log
import com.example.domain.entity.NoteEntity
import com.example.domain.entity.NoteEntity.Companion.NOTE_COLLECTION
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
        val noteDocRef = mFirebaseFireStore
            .collection(USER_COLLECTION)
            .document(note.uid)
            .collection(TRIP_COLLECTION)
            .document(note.tripId)
            .collection(NOTE_COLLECTION)
            .document()

        note.id = noteDocRef.id

        noteDocRef.set(note).addOnSuccessListener {
            Log.e("FIB Firestore", "note added successfully")
            onSuccess()
        }.addOnFailureListener {
            Log.e("FIB Firestore", "Error : ${it.message}")
            onFailure(it)
        }
    }

    override fun getNotes(
        uid: String,
        tripId: String,
        onSuccess: (List<NoteEntity>) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        val noteDocRef = mFirebaseFireStore
            .collection(USER_COLLECTION)
            .document(uid)
            .collection(TRIP_COLLECTION)
            .document(tripId)
            .collection(NOTE_COLLECTION)

        noteDocRef.addSnapshotListener { snapShot , error ->
            if(error != null){
                onFailure(error)
                Log.e("FIB FireStore" , "Error : ${error.message}")
            }

            if(snapShot != null && !snapShot.isEmpty){
                val notes = snapShot.toObjects(NoteEntity::class.java)
                Log.e("FIB FireStore data source" , "notes retrieved")
                onSuccess(notes)
            }else{
                Log.e("FIB FireStore data source", "No notes found")
                onSuccess(emptyList())
            }
        }
    }

}