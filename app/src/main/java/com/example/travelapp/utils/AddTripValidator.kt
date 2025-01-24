package com.example.travelapp.utils

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.data.mapper.localDateToMillis
import com.example.data.mapper.millisToLocalDate
import com.example.data.mapper.textToLocalTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun areAddTripFieldsValid(
    tripName : String,
    tripNameErrorState : MutableState<String>,
    startPoint : String,
    startPointErrorState : MutableState<String>,
    endPoint : String,
    endPointErrorState : MutableState<String>,
    startDateMillis : Long,
    dateErrorState : MutableState<String>,
    startTime : String?,
    startTimeErrorState : MutableState<String>,
    endDateMillis : Long,
    isRoundTrip : Boolean,
    roundTripDateErrorState :MutableState<String>,
    endTripTime :String?,
    endTripTimeErrorState :MutableState<String>,
) : Boolean{
    if(!isTripNameValid(tripName,tripNameErrorState)) return false

    if(!isStartPointValid(startPoint,startPointErrorState)) return false

    if(!isEndPointValid(endPoint,endPointErrorState)) return false

    if(!isTripStartPointDateValid(startDateMillis,dateErrorState)) return false

    if(!isTripStartTimeValid(startTime,startDateMillis,startTimeErrorState)) return false

    if(isRoundTrip){
        if(!isTripEndPointDateValid(startDateMillis,endDateMillis,roundTripDateErrorState)) return false
        if(!isTripEndTimeValid(endTripTime,startTime,startDateMillis,endDateMillis,endTripTimeErrorState)) return false
    }

    return true
}

fun isTripNameValid(
    tripName: String,
    tripNameErrorState : MutableState<String>
) : Boolean{
    if(tripName.isEmpty()){
        tripNameErrorState.value = "Start Point Field is Required"
        return false
    }
    return true
}

fun isStartPointValid(startPoint: String,startPointErrorState:MutableState<String>) : Boolean{
     if(startPoint.isEmpty()){
         startPointErrorState.value = "Start Point Field is Required"
         return false
    }
    return true
}

fun isEndPointValid(endPoint: String,endPointErrorState:MutableState<String>) : Boolean{
    if(endPoint.isEmpty()){
        endPointErrorState.value = "End Point Field is Required"
        return false
    }
    return true
}

fun isTripStartTimeValid(
    time : String?,
    selectedDateMillis: Long,
    timeErrorState: MutableState<String>
) : Boolean{
    if(time == null){
        timeErrorState.value = "Time Field is required"
        return false
    }

    val currentDateMillis = localDateToMillis(LocalDate.now())
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    val localTime = LocalTime.parse(time,formatter)

    if(selectedDateMillis == currentDateMillis){
        Log.e("Time validation" , "selectedDateMillis == currentDateMillis is true")
         val currentTime = LocalTime.now()
         if(localTime.isBefore(currentTime)){
             Log.e("Time validation" , "localTime.isBefore(currentTime) is true")
             timeErrorState.value = "You can't select time in the past to your today's trip"
             return false
         }
    }

    timeErrorState.value = ""
    return true
}

fun isTripEndTimeValid(
    endTime : String?,
    startTime : String?,
    selectedStartDateMillis: Long,
    selectedEndDateMillis: Long,
    timeErrorState: MutableState<String>
) : Boolean{

    if(endTime == null){
        timeErrorState.value = "Time Field is required"
        return false
    }

    if(startTime == null){
        timeErrorState.value = "Trip start time can't be empty"
        return false
    }

    if(selectedStartDateMillis == selectedEndDateMillis){
        if(textToLocalTime(endTime).isBefore(textToLocalTime(startTime))){
            timeErrorState.value = "Trip end time can't be before start time"
            return false
        }
    }
    timeErrorState.value = ""
    return true
}

fun isTripStartPointDateValid(selectedMillis : Long, selectedDateErrorState : MutableState<String>) : Boolean{
    return isSelectedDateAfterCurrent(selectedMillis, selectedDateErrorState)
}

fun isTripEndPointDateValid(
    firstTripDateMillis : Long,
    secondTripDateMillis : Long,
    selectedDateErrorState : MutableState<String>
) : Boolean{
    return isSelectedDateAfterCurrent(secondTripDateMillis, selectedDateErrorState) &&
            isFirstTripDateAfterSecondTripDate(firstTripDateMillis, secondTripDateMillis, selectedDateErrorState)
}

private fun isSelectedDateAfterCurrent(
    selectedMillis : Long,
    selectedDateErrorState : MutableState<String>
): Boolean{
    val selectedDate = millisToLocalDate(selectedMillis)

    val currentDate = LocalDate.now()

    if (selectedDate.isBefore(currentDate)){
        selectedDateErrorState.value = "Selected date cannot be before today!"
        return false
    }

    selectedDateErrorState.value = ""
    return true
}

private fun isFirstTripDateAfterSecondTripDate(
    firstTripDateMillis : Long,
    secondTripDateMillis : Long,
    selectedDateErrorState : MutableState<String>
) : Boolean{
    val firstDate = millisToLocalDate(firstTripDateMillis)
    val secondDate = millisToLocalDate(secondTripDateMillis)

    if (secondDate.isBefore(firstDate)){
        selectedDateErrorState.value = "Selected second trip date cannot be before your first trip date!"
        return false
    }
    selectedDateErrorState.value = ""
    return true
}

