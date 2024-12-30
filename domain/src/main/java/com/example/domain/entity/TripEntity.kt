package com.example.domain.entity

data class TripEntity(
    val id : String? = null,
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
){
    companion object {
        const val TRIP_COLLECTION = "trips"

        const val ONE_DIRECTION_TRIP = "One Direction"
        const val ROUND_TRIP = "Round Trip"

        const val UPCOMING = "Upcoming"
        const val DONE = "Done"
        const val CANCELLED = "Cancelled"
    }
}
