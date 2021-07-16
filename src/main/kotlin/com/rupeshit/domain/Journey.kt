package com.rupeshit.domain

import java.time.LocalTime

data class Journey(val day: String, val time: LocalTime, val fromZone: Zone, val toZone: Zone, val week: String = "Week-1" )

data class Day(val name: String, val maxWeeklyCap: Double = 0.0){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Day

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}



