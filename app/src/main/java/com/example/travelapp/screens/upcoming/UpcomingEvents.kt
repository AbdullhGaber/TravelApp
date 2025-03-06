package com.example.travelapp.screens.upcoming

import androidx.annotation.IntRange
import com.example.domain.entity.TripEntity

sealed class UpcomingEvents {
    data class OnTripReminderDialogCancelClick(val tripId : String) : UpcomingEvents()
    data class OnTripCardDeleteClick(val trip : TripEntity) : UpcomingEvents()
    data class OnUndoDeleteClick(val trip : TripEntity) : UpcomingEvents()
}