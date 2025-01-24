package com.example.data.mapper

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun textToLocalTime(timeStr : String) : LocalTime{
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    val localTime = LocalTime.parse(timeStr,formatter)
    return localTime
}

fun localTimeToText(localTime: LocalTime) : String{
    return localTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
}