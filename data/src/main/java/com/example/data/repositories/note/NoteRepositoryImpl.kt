package com.example.data.repositories.note

import com.example.domain.entity.NoteEntity
import com.example.domain.repositories.note.NoteOfflineDataSource
import com.example.domain.repositories.note.NoteRemoteDataSource
import com.example.domain.repositories.note.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val mNoteOfflineDataSource: NoteOfflineDataSource,
    private val mNoteRemoteDataSource: NoteRemoteDataSource
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
}