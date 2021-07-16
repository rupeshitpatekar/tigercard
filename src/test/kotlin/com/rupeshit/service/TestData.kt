package com.rupeshit.service

import com.rupeshit.domain.Day
import com.rupeshit.domain.PeakHour
import com.rupeshit.domain.Zone
import java.time.LocalTime

fun exampleZoneOne() = Zone(
    "Zone-1",
    exampleListOfPeakHours(),
    peakHoursFare = 30.0,
    offPeakHoursFare = 25.0,
    dailyCap = 100.0,
    weeklyCap = 500.0
)

fun exampleZoneTwo() = Zone(
    "Zone-2",
    exampleListOfPeakHours(),
    peakHoursFare = 25.0,
    offPeakHoursFare = 20.0,
    dailyCap = 80.0,
    weeklyCap = 400.0
)

private fun exampleListOfPeakHours() = listOf(
    PeakHour("Monday", LocalTime.of(7, 0), LocalTime.of(10, 30)),
    PeakHour("Monday", LocalTime.of(17, 0), LocalTime.of(20, 0)),

    PeakHour("Tuesday", LocalTime.of(7, 0), LocalTime.of(10, 30)),
    PeakHour("Tuesday", LocalTime.of(17, 0), LocalTime.of(20, 0)),

    PeakHour("Wednesday", LocalTime.of(7, 0), LocalTime.of(10, 30)),
    PeakHour("Wednesday", LocalTime.of(17, 0), LocalTime.of(20, 0)),

    PeakHour("Thursday", LocalTime.of(7, 0), LocalTime.of(10, 30)),
    PeakHour("Thursday", LocalTime.of(17, 0), LocalTime.of(20, 0)),

    PeakHour("Friday", LocalTime.of(7, 0), LocalTime.of(10, 30)),
    PeakHour("Friday", LocalTime.of(17, 0), LocalTime.of(20, 0)),

    PeakHour("Saturday", LocalTime.of(9, 0), LocalTime.of(11, 0)),
    PeakHour("Saturday", LocalTime.of(18, 0), LocalTime.of(22, 0)),

    PeakHour("Sunday", LocalTime.of(9, 0), LocalTime.of(11, 0)),
    PeakHour("Sunday", LocalTime.of(18, 0), LocalTime.of(22, 0))
)

