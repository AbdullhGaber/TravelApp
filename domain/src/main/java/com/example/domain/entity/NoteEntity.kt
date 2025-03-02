package com.example.domain.entity

data class NoteEntity(
    var id : String = "",
    val text : String = "",
    val tripId : String = "",
    val uid : String = "",
){
    companion object{
        const val NOTE_COLLECTION = "notes"
    }
}
