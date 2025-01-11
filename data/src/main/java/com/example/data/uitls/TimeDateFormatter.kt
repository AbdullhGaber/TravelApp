package com.example.data.uitls

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimeDate(time : String , date : String) : Date?{
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return formatter.parse("$date $time")
}