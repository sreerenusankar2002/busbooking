package com.example.bus.repositoryTest

import com.example.bus.repository.BusRepository

import com.example.bus.entity.Bus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BusRepositoryTest {

    @Autowired
    private lateinit var busRepository: BusRepository

    private lateinit var bus1: Bus
    private lateinit var bus2: Bus

    @BeforeEach
    fun setUp() {

        bus1 = Bus(null, "TN01", "MM Travels", "RedBus", 50, 40, "Chennai", "Coimbatore")
        bus2 = Bus(null, "TN02", "ABC Travels", "MakeMyTrip", 40, 30, "Bangalore", "Kochi")
        busRepository.save(bus1)
        busRepository.save(bus2)
    }

    @Test
    fun `findBySourceAndDestination should return the correct buses`() {
        val result = busRepository.findBySourceAndDestination("Chennai", "Coimbatore")

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals("MM Travels", result[0].busName)
    }

    @Test
    fun `findBySourceAndDestination should return empty list when no buses found`() {

        val result = busRepository.findBySourceAndDestination("Hyderabad", "Pune")

        assertTrue(result.isEmpty())
    }

    @Test
    fun `findByBusNumber should return the correct bus`() {

        val result = busRepository.findByBusNumber("TN01")

        assertTrue(result.isPresent)
        assertEquals(bus1.busName, result.get().busName)
    }

    @Test
    fun `findByBusNumber should return empty when bus is not found`() {

        val result = busRepository.findByBusNumber("TN03")

        assertFalse(result.isPresent)
    }
}
