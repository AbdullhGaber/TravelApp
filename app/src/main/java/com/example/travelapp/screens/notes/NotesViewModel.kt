package com.example.travelapp.screens.notes

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.DataUtil
import com.example.data.uitls.Resource
import com.example.domain.entity.NoteEntity
import com.example.domain.use_cases.note.NoteUseCases
import com.example.travelapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val mNoteUseCases : NoteUseCases,
    @ApplicationContext private val mContext : Context
) : ViewModel() {
    private val _addTripState = MutableSharedFlow<String>()
    val addTripState = _addTripState.asSharedFlow()

    private val _notesStateFlow = MutableStateFlow<Resource<List<NoteEntity>?>>(Resource.Unspecified())
    val notesStateFlow = _notesStateFlow.asStateFlow()

    val noteTextState = mutableStateOf("")

    fun updateNoteText(text : String) {noteTextState.value = text}

    fun onEvent(event:NotesScreenEvent){
        when(event){
            is NotesScreenEvent.OnAddButtonClick -> {
                val note = NoteEntity(
                    id = UUID.randomUUID().toString(),
                    text = noteTextState.value,
                    uid = DataUtil.tripUser?.uid ?: "",
                    tripId = event.tripId,
                    addedTime = System.currentTimeMillis()
                )
                addNote(note)
                noteTextState.value = ""
            }
            is NotesScreenEvent.OnErrorDialogDismiss -> {
                _notesStateFlow.value = Resource.Unspecified()
            }
        }
    }

    fun getTripNotes(tripId : String){
        _notesStateFlow.value = Resource.Loading()

        mNoteUseCases.getNotesUseCase(
                tripId = tripId,
                uid = DataUtil.tripUser?.uid!!,
                onSuccess = { notes ->
                    viewModelScope.launch {
                        _notesStateFlow.emit(Resource.Success(notes.sortedBy { it.addedTime }))
                    }
                },
                onFailure = {
                    viewModelScope.launch {
                        _notesStateFlow.emit(Resource.Failure(it.message))
                    }
                }
       )
    }
    private fun addNote(note : NoteEntity){
        mNoteUseCases.addNoteUseCase(
            note = note,
            onSuccess = {
                viewModelScope.launch {
                    _addTripState.emit(mContext.getString(R.string.note_added_successfully))
                }
            },
            onFailure = {
                viewModelScope.launch {
                    _addTripState.emit(mContext.getString(R.string.something_went_wrong))
                }
            }
        )
    }
}