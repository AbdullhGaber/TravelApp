package com.example.domain.use_cases.note

import com.example.domain.entity.NoteEntity
import com.example.domain.repositories.note.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val mNoteRepository: NoteRepository
) {
    operator fun invoke(
        uid : String,
        tripId : String,
        onSuccess: (List<NoteEntity>) -> Unit,
        onFailure: (Throwable) -> Unit
    ){
        mNoteRepository.getNotes(uid, tripId, onSuccess, onFailure)
    }
}