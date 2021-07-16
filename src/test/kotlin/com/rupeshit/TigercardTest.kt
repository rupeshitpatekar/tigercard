package com.rupeshit
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class TigercardTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Test
    fun `application should be in running state`() {
        Assertions.assertTrue(application.isRunning)
    }

}
