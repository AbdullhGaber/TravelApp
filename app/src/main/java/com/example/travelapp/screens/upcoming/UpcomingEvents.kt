package com.example.travelapp.screens.upcoming

import androidx.annotation.IntRange

sealed class UpcomingEvents {
    data class OnTripReminderDialogCancelClick(val id : String, @IntRange(0,1) val value : Int) : UpcomingEvents()
}