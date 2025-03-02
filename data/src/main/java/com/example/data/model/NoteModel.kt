package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int = -1,
    val text : String = "",
    val tripId : String = "",
    val uid : String = "",
)
