package com.rupeshit.strategy

import com.rupeshit.domain.FareStrategy
import com.rupeshit.domain.Journey
import com.rupeshit.service.ZoneCapFareService
import javax.inject.Inject

class DailyFareStrategy: FareStrategy {

    @Inject
    lateinit var crossZoneService: ZoneCapFareService

    override fun calculateFare(journeys: List<Journey>): Double {
        var sum = 0.0
        var maxCap = journeys.maxCap()
        journeys.forEach { journey ->
            sum += if(!journey.isCrossZone()) {
                journey.fromZone.getFare(journey.day, journey.time)
            }else {
                crossZoneService.getCrossZoneFare(journey.fromZone.name,
                    journey.toZone.name,
                    journey.fromZone.isPeakHours(journey.day, journey.time)
                )
            }
            if(sum > maxCap ) return maxCap
        }
        return sum
    }

    private fun Journey.isCrossZone() = fromZone.name != toZone.name

    private fun List<Journey>.maxCap (): Double {
        var maxCap = 0.0
        forEach { journey ->
            if(!journey.isCrossZone()) {
                if(maxCap < journey.fromZone.dailyCap) maxCap = journey.fromZone.dailyCap
            }else{
                val crossZoneDailyCap = crossZoneService.getCrossZoneDailyCap(journey.fromZone.name, journey.toZone.name)
                if(maxCap < crossZoneDailyCap) maxCap = crossZoneDailyCap
            }
        }
        return maxCap
    }
}