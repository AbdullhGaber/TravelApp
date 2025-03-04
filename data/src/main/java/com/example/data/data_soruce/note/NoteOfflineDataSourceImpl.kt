package com.example.data.data_soruce.note

import com.example.data.database.NoteDao
import com.example.data.mapper.toEntity
import com.example.data.mapper.toModel
import com.example.domain.entity.NoteEntity
import com.example.domain.repositories.note.NoteOfflineDataSource
import kotlinx.coroutines.flow.map
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

    override suspend fun getNotes(
        tripId: String,
        onSuccess: (List<NoteEntity>) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        try {
            mNoteDao.getTripNotes(tripId).map { it?.map { it.toEntity() } }.collect{ trips ->
                onSuccess(trips?: emptyList())
            }
        }catch (e : Exception){
            onFailure(e)
        }
    }
}