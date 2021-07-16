package com.rupeshit.domain

data class CrossZone(
    val from: String,
    val to: String,
    val peakHoursFare: Double,
    val offPeakHoursFare: Double,
    val dailyCap: Double,
    val weeklyCap: Double
)


