package com.rupeshit.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class ZoneCapFareServiceTest {
    @Inject
    lateinit var crossZoneService: ZoneCapFareService

    @Test
    fun `should return cross zone fares`(){
        assertEquals(35.0, crossZoneService.getCrossZoneFare("Zone-2","Zone-1",true))
    }

    @Test
    fun `should return cross zone daily cap`(){
        assertEquals(120.0, crossZoneService.getCrossZoneDailyCap("Zone-2","Zone-1"))
    }

    @Test
    fun `should return cross zone weekly cap`(){
        assertEquals(600.0, crossZoneService.getCrossZoneWeeklyCap("Zone-2","Zone-1"))
    }

    @Test
    fun `should not return positive cross zone fares if zone is not exists`(){
        assertEquals(0.0, crossZoneService.getCrossZoneFare("Zone-X","Zone-1",true))
    }

}