package com.rupeshit.domain

interface FareStrategy {
    fun calculateFare(journeys: List<Journey>): Double
}