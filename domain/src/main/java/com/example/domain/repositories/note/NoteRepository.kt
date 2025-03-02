package com.example.domain.repositories.note

import com.example.domain.entity.NoteEntity

interface NoteRepository {
    fun addNote(note : NoteEntity, onSuccess : () -> Unit,onFailure: (Throwable) -> Unit)
}

interface NoteRemoteDataSource{
    fun addNote(note : NoteEntity , onSuccess : () -> Unit , onFailure: (Throwable) -> Unit)
}
interface NoteOfflineDataSource{
    suspend fun addNote(note : NoteEntity , onSuccess : () -> Unit , onFailure: (Throwable) -> Unit)
}