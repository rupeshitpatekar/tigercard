package com.rupeshit.service

import com.rupeshit.domain.CrossZone
import com.rupeshit.domain.Day
import com.rupeshit.domain.Zone
import java.time.LocalTime
import javax.inject.Singleton

@Singleton
class ZoneCapFareService {

    fun getCrossZoneFare(from: String, to: String, isPeakHour: Boolean): Double {
        loadCrossZoneFares().map { zone ->
            if ((zone.from == from || zone.to == from) && (zone.from == to || zone.to == to)) {
                return if(isPeakHour) zone.peakHoursFare else zone.offPeakHoursFare
            } else {
                println("Cross zone fare not found.")
            }
        }
        return 0.0
    }

    fun getCrossZoneDailyCap(from: String, to: String): Double {
        loadCrossZoneFares().map { zone ->
            if ((zone.from == from || zone.to == from) && (zone.from == to || zone.to == to)) {
                return zone.dailyCap
            } else {
                println("Cross zone fare not found.")
            }
        }
        return 0.0
    }

    fun getCrossZoneWeeklyCap(from: String, to: String): Double {
        loadCrossZoneFares().map { zone ->
            if ((zone.from == from || zone.to == from) && (zone.from == to || zone.to == to)) {
                return zone.weeklyCap
            } else {
                println("Cross zone fare not found.")
            }
        }
        return 0.0
    }

    private fun loadCrossZoneFares() = listOf(
        CrossZone("Zone-1", "Zone-2", 35.0,30.0,120.0, 600.0)
    )
}