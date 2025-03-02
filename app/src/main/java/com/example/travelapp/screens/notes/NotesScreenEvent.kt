package com.example.travelapp.screens.notes

sealed class NotesScreenEvent {
    data class OnAddButtonClick(val tripId: String) : NotesScreenEvent()
}