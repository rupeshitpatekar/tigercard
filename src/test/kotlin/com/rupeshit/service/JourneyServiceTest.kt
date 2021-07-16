package com.rupeshit.service

import com.rupeshit.domain.Journey
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalTime
import javax.inject.Inject

@MicronautTest
class JourneyServiceTest {

    @Inject
    private lateinit var journeyService: JourneyService

    @Test
    fun `should calculate fare for journeys`(){
        val journeys = listOf(
            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),
            Journey(day="Monday", time = LocalTime.of(11, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),
            Journey(day="Monday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne())
        )
        assertEquals(80.0, journeyService.calculateDailyFare(journeys))
    }

    @Test
    fun `should calculate fare for journeys on the basis of daily cap`(){
        val journeys = listOf(
            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),
            Journey(day="Monday", time = LocalTime.of(11, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),
            Journey(day="Monday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),
            Journey(day="Monday", time = LocalTime.of(13, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne())
        )
        assertEquals(100.0, journeyService.calculateDailyFare(journeys))
    }

    @Test
    fun `should calculate fare for journeys in different zones`(){
        val journeys = listOf(
            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),
            Journey(day="Monday", time = LocalTime.of(11, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),
            Journey(day="Monday", time = LocalTime.of(12, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne())
        )
        assertEquals(90.0, journeyService.calculateDailyFare(journeys))
    }

    @Test
    fun `should calculate fare for journeys across multiple zones on the basis of daily cap`(){
        val journeys = listOf(
            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),
            Journey(day="Monday", time = LocalTime.of(11, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),
            Journey(day="Monday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),
            Journey(day="Monday", time = LocalTime.of(13, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),
            Journey(day="Monday", time = LocalTime.of(17, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),
        )
        assertEquals(120.0, journeyService.calculateDailyFare(journeys))
    }

    @Test
    fun `should calculate weekly fare for journeys`(){
        val journeys = listOf(
            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),  //30
            Journey(day="Monday", time = LocalTime.of(4, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),   //25
            Journey(day="Tuesday", time = LocalTime.of(11, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),  //25
            Journey(day="Wednesday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),    //35
            Journey(day="Thursday", time = LocalTime.of(7, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),      //35
            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne(), week = "Week-2"), //30
        )
        assertEquals(180.0, journeyService.calculateWeeklyFare(journeys))
    }

    @Test
    fun `should calculate fare for journeys across multiple zones on the basis of weekly cap`(){
        val journeys = listOf(
            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//35
            Journey(day="Monday", time = LocalTime.of(11, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Monday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//30
            Journey(day="Monday", time = LocalTime.of(13, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Monday", time = LocalTime.of(17, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),//30 totalFare - 120

            Journey(day="Tuesday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//35
            Journey(day="Tuesday", time = LocalTime.of(11, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Tuesday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//30
            Journey(day="Tuesday", time = LocalTime.of(13, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Tuesday", time = LocalTime.of(17, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),//30  totalFare - 240

            Journey(day="Wednesday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//35
            Journey(day="Wednesday", time = LocalTime.of(11, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Wednesday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//30
            Journey(day="Wednesday", time = LocalTime.of(13, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Wednesday", time = LocalTime.of(17, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),//30  totalFare - 360

            Journey(day="Thursday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//35
            Journey(day="Thursday", time = LocalTime.of(11, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Thursday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//30
            Journey(day="Thursday", time = LocalTime.of(13, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Thursday", time = LocalTime.of(17, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),//30   totalFare - 480

            Journey(day="Friday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//35
            Journey(day="Friday", time = LocalTime.of(11, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Friday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//30
            Journey(day="Friday", time = LocalTime.of(13, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Friday", time = LocalTime.of(17, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),//30    totalFare - 600

            Journey(day="Saturday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//35
            Journey(day="Saturday", time = LocalTime.of(11, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Saturday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//30
            Journey(day="Saturday", time = LocalTime.of(13, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Saturday", time = LocalTime.of(17, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),//30   WeeklyCap reach  totalFare =600

            Journey(day="Sunday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//35
            Journey(day="Sunday", time = LocalTime.of(11, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Sunday", time = LocalTime.of(12, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo()),//30
            Journey(day="Sunday", time = LocalTime.of(13, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne()),//30
            Journey(day="Sunday", time = LocalTime.of(17, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),//30   WeeklyCap reach totalFare = 600

            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo(), week = "Week-2"),//35
            Journey(day="Monday", time = LocalTime.of(11, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne(), week = "Week-2"),//30   totalFare = 665
        )
        assertEquals(665.0, journeyService.calculateWeeklyFare(journeys))
    }

    @Test
    fun `should calculate multiple weekly fares for journeys`(){
        val journeys = listOf(
            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),  //30
            Journey(day="Tuesday", time = LocalTime.of(11, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne()),  //25
            Journey(day="Wednesday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneTwo(), week = "Week-2"),    //35
            Journey(day="Thursday", time = LocalTime.of(7, 30), fromZone = exampleZoneTwo(), toZone = exampleZoneOne(), week = "Week-3"),      //35
            Journey(day="Monday", time = LocalTime.of(7, 30), fromZone = exampleZoneOne(), toZone = exampleZoneOne(), week = "Week-3"), //30
        )
        assertEquals(155.0, journeyService.calculateWeeklyFare(journeys))
    }

}