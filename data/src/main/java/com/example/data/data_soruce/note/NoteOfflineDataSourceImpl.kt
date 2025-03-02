package com.example.data.data_soruce.note

import com.example.data.database.NoteDao
import com.example.data.mapper.toModel
import com.example.domain.entity.NoteEntity
import com.example.domain.repositories.note.NoteOfflineDataSource
import javax.inject.Inject

class NoteOfflineDataSourceImpl @Inject constructor(
    private val mNoteDao : NoteDao
) : NoteOfflineDataSource {
    override suspend fun addNote(note: NoteEntity, onSuccess: () -> Unit, onFailure : (Throwable) -> Unit) {
        try {
            mNoteDao.addNote(note.toModel())
            onSuccess()
        }catch (e : Exception){
            onFailure(e)
        }
    }
}