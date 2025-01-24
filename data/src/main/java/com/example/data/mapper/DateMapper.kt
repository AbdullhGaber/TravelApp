package com.example.data.mapper


import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun localDateToMillis(localDate: LocalDate, zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return localDate
        .atTime(LocalTime.MIDNIGHT)
        .atZone(zoneId)
        .toInstant()
        .toEpochMilli()
}

fun millisToLocalDate(selectedMillis: Long): LocalDate =
    Instant.ofEpochMilli(selectedMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

fun textToLocalDate(dateStr : String?) : LocalDate{
    val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
    return LocalDate.parse(dateStr ?: LocalDate.now().format(formatter), formatter)
}

