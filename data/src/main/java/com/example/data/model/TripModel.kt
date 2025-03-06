package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripModel(
    @PrimaryKey
    var id : String = "0",
    val uid : String = "0",
    val notes : List<String> = emptyList(),
    val status : String = "",
    val name : String = "Trip",
    val startDestination : String = "",
    val endDestination : String = "",
    val date : String = "",
    val time : String = "",
    val returnDate : String? = null,
    val returnTime : String? = null,
    val type : String = "",
    var hasFirstTripTimeCome : Boolean = false,
    var hasSecondTripTimeCome : Boolean = false
)