package com.example.data.repositories.note

import android.content.Context
import com.example.data.uitls.NetworkUtil
import com.example.domain.entity.NoteEntity
import com.example.domain.repositories.note.NoteOfflineDataSource
import com.example.domain.repositories.note.NoteRemoteDataSource
import com.example.domain.repositories.note.NoteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val mNoteOfflineDataSource: NoteOfflineDataSource,
    private val mNoteRemoteDataSource: NoteRemoteDataSource,
    @ApplicationContext private val mContext : Context
) : NoteRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    override fun addNote(note: NoteEntity, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        mNoteRemoteDataSource.addNote(
            note = note,
            onSuccess = {
                coroutineScope.launch {
                    mNoteOfflineDataSource.addNote(
                        note = note,
                        onSuccess = onSuccess,
                        onFailure = onFailure
                    )
                }
            },
            onFailure = onFailure
        )
    }

    override fun getNotes(
        uid: String,
        tripId:String,
        onSuccess: (List<NoteEntity>) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        if(NetworkUtil.isDeviceConnected(mContext)){
            mNoteRemoteDataSource.getNotes(uid, tripId, onSuccess, onFailure)
        }else{
            coroutineScope.launch {
                mNoteOfflineDataSource.getNotes(tripId, onSuccess, onFailure)
            }
        }
    }
}