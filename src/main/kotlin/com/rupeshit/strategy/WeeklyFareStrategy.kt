package com.rupeshit.strategy

import com.rupeshit.domain.Day
import com.rupeshit.domain.FareStrategy
import com.rupeshit.domain.Journey
import com.rupeshit.service.ZoneCapFareService
import javax.inject.Inject

class WeeklyFareStrategy: FareStrategy {

    @Inject
    lateinit var crossZoneService: ZoneCapFareService

    @Inject
    lateinit var dailyFareStrategy: DailyFareStrategy

    override fun calculateFare(journeys: List<Journey>): Double {
        var totalSum = 0.0
        var weeklySum: Double
        val weekMap = getWeeklyJourneys(journeys)
        var weeklyCap = 0.0
        weekMap.keys.forEach { week ->
            weeklySum = 0.0
            run {
                weeklySum += weekMap[week]?.map { it ->
                    if(weeklyCap < it.key.maxWeeklyCap) weeklyCap = it.key.maxWeeklyCap
                    dailyFareStrategy.calculateFare(it.value)
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