package com.rupeshit.service

import com.rupeshit.domain.Journey
import com.rupeshit.domain.FareStrategy
import javax.inject.Singleton

@Singleton
class JourneyService {

    fun calculateFare(fareStrategy: FareStrategy, journeys: List<Journey>): Double{
        return fareStrategy.calculateFare(journeys)
    }

}