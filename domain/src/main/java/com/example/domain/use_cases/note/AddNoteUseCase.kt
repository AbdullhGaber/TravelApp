package com.example.domain.use_cases.note

import com.example.domain.entity.NoteEntity
import com.example.domain.repositories.note.NoteRepository

class AddNoteUseCase(
    private val mNoteRepository: NoteRepository
){
    operator fun invoke(note : NoteEntity, onSuccess : () -> Unit, onFailure: (Throwable) -> Unit){
        mNoteRepository.addNote(note, onSuccess, onFailure)
    }
}