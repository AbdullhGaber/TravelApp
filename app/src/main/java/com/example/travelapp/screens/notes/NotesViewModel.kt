package com.example.travelapp.screens.notes

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.uitls.DataUtil
import com.example.domain.entity.NoteEntity
import com.example.domain.use_cases.note.NoteUseCases
import com.example.travelapp.R
import com.google.android.gms.common.util.DataUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val mNoteUseCases : NoteUseCases,
    @ApplicationContext private val mContext : Context
) : ViewModel() {
    private val _addTripState = MutableSharedFlow<String>()
    val addTripState = _addTripState.asSharedFlow()

    val noteTextState = mutableStateOf("")

    fun updateNoteText(text : String) {noteTextState.value = text}

    fun onEvent(event:NotesScreenEvent){
        when(event){
            is NotesScreenEvent.OnAddButtonClick -> {
                val note = NoteEntity(
                    text = noteTextState.value,
                    uid = DataUtil.tripUser?.uid ?: "",
                    tripId = event.tripId
                )
                addNote(note)
                noteTextState.value = ""
            }
        }
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