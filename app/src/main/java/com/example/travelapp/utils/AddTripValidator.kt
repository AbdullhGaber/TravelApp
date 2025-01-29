package com.example.travelapp.utils

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.data.mapper.localDateToMillis
import com.example.data.mapper.millisToLocalDate
import com.example.data.mapper.textToLocalTime
import com.example.data.uitls.DataUtil
import com.example.data.uitls.mContext
import com.example.travelapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


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
        tripNameErrorState.value = mContext.getString(R.string.trip_name_field_is_required)
        return false
    }
    return true
}

fun isStartPointValid(startPoint: String,startPointErrorState:MutableState<String>) : Boolean{
     if(startPoint.isEmpty()){
         startPointErrorState.value = mContext.getString(R.string.start_point_field_is_required)
         return false
    }
    return true
}

fun isEndPointValid(endPoint: String,endPointErrorState:MutableState<String>) : Boolean{
    if(endPoint.isEmpty()){
        endPointErrorState.value = mContext.getString(R.string.end_point_field_is_required)
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
        timeErrorState.value = mContext.getString(R.string.time_field_is_required)
        return false
    }

    val currentDateMillis = localDateToMillis(LocalDate.now())
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    val localTime = LocalTime.parse(time,formatter)

    if(selectedDateMillis == currentDateMillis){
         val currentTime = LocalTime.now()
         if(localTime.isBefore(currentTime)){
             timeErrorState.value =
                 mContext.getString(R.string.you_can_t_select_time_in_the_past_to_your_today_s_trip)
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
        timeErrorState.value = mContext.getString(R.string.time_field_is_required)
        return false
    }

    if(startTime == null){
        timeErrorState.value = mContext.getString(R.string.trip_start_time_can_t_be_empty)
        return false
    }

    if(selectedStartDateMillis == selectedEndDateMillis){
        if(textToLocalTime(endTime).isBefore(textToLocalTime(startTime))){
            timeErrorState.value =
                mContext.getString(R.string.trip_end_time_can_t_be_before_start_time)
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
        selectedDateErrorState.value =
            mContext.getString(R.string.selected_date_cannot_be_before_today)
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
        selectedDateErrorState.value =
            mContext.getString(R.string.selected_second_trip_date_cannot_be_before_your_first_trip_date)
        return false
    }
    selectedDateErrorState.value = ""
    return true
}

