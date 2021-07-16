package com.rupeshit.service

import com.rupeshit.domain.Day
import com.rupeshit.domain.Journey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JourneyService {

    @Inject
    lateinit var crossZoneService: ZoneCapFareService

    fun calculateDailyFare(journeys: List<Journey>): Double{
        var sum = 0.0
        var maxDailyCap = 0.0
        journeys.forEach { journey ->
            if(journey.fromZone.name == journey.toZone.name) {
                sum += journey.fromZone.getFare(journey.day, journey.time)
                if(maxDailyCap < journey.fromZone.dailyCap) maxDailyCap = journey.fromZone.dailyCap
            }else {
                sum += crossZoneService.getCrossZoneFare(journey.fromZone.name,
                    journey.toZone.name,
                    journey.fromZone.isPeakHours(journey.day, journey.time)
                )
                val crossZoneDailyCap = crossZoneService.getCrossZoneDailyCap(journey.fromZone.name, journey.toZone.name)
                if(maxDailyCap < crossZoneDailyCap) maxDailyCap = crossZoneDailyCap
            }
            if(sum > maxDailyCap ) sum = maxDailyCap
        }
        return sum
    }

    fun calculateWeeklyFare(journeys: List<Journey>): Double{
        var totalSum = 0.0
        var weeklySum: Double
        val weekMap = getWeeklyJourneys(journeys)
        var weeklyCap = 0.0
        weekMap.keys.forEach { week ->
            weeklySum = 0.0
            run {
                weeklySum += weekMap[week]?.map { it ->
                    if(weeklyCap < it.key.maxWeeklyCap) weeklyCap = it.key.maxWeeklyCap
                    calculateDailyFare(it.value)
                }?.let { it.sum() } ?: 0.0
            }
            if(weeklySum > weeklyCap) weeklySum = weeklyCap
            totalSum += weeklySum
        }
        return totalSum
    }

    private fun getWeeklyJourneys(journeys: List<Journey>): MutableMap<String, Map<Day, List<Journey>>> {
        val weekMap = mutableMapOf<String, Map<Day, List<Journey>>>()
        journeys.forEach { journey ->
            weekMap.compute(journey.week) { _, map ->
                map?.let { getDailyJourneys(it.toMutableMap(), journey) } ?: getDailyJourneys(mutableMapOf(), journey)
            }
        }
        return weekMap
    }

    private fun getDailyJourneys(
        dayMap: MutableMap<Day, List<Journey>>,
        journey: Journey
    ): MutableMap<Day, List<Journey>> {

        val currentWeeklyCap = journey.getWeeklyCapForJourney()
        var key = Day(journey.day, currentWeeklyCap)

        if(dayMap[key] != null){
            key = dayMap.keys.find { it == key }!!
            if(key.maxWeeklyCap < currentWeeklyCap) key = key.copy(maxWeeklyCap = currentWeeklyCap)
            dayMap[key] = dayMap[key]!!.plus(journey)
        }else dayMap[key] = mutableListOf(journey)

        return dayMap
    }

    private fun Journey.getWeeklyCapForJourney(): Double {
        return if(fromZone.name != toZone.name)
            crossZoneService.getCrossZoneWeeklyCap(fromZone.name, toZone.name)
        else
            fromZone.weeklyCap
    }
}