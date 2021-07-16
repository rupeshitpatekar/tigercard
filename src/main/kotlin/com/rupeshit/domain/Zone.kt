package com.rupeshit.domain

import java.time.LocalTime

data class Zone(
    val name: String,
    val peakHours: List<PeakHour>,
    val peakHoursFare: Double,
    val offPeakHoursFare: Double,
    val dailyCap: Double,
    val weeklyCap: Double
){
    fun getFare(day: String, time: LocalTime): Double {
        return if(isPeakHours(day, time)) peakHoursFare else offPeakHoursFare
    }

    fun isPeakHours(day: String, time: LocalTime) = peakHours.any { pickHour ->
        pickHour.day == day
                && time.isAfter(pickHour.startTime)
                && time.isBefore(pickHour.endTime)
    }

}
