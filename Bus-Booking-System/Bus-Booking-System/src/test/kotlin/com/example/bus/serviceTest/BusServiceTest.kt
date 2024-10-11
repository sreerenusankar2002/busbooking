package com.example.bus.serviceTest

import com.example.bus.dto.BusDTO
import com.example.bus.dto.UpdateBusDTO
import com.example.bus.entity.Bus
import com.example.bus.exception.BusNotFoundException
import com.example.bus.repository.BusRepository
import com.example.bus.service.BusService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.util.*

class BusServiceTest {

    private lateinit var busService: BusService
    private lateinit var busRepository: BusRepository

    @BeforeEach
    fun setUp() {
        busRepository = mock(BusRepository::class.java)
        busService = BusService(busRepository)
    }

    @Test
    fun `createBus should create and return BusDTO when bus does not already exist`() {

        val busDTO = BusDTO(null, "TN01", "MM Travels", "Red Bus", 50, 40, "Chennai", "Coimbatore")
        val savedBus = Bus(1L, "TN01", "MM Travels", "Red Bus", 50, 40, "Chennai", "Coimbatore")

        `when`(busRepository.findByBusNumber(busDTO.busNumber)).thenReturn(Optional.empty())
        `when`(busRepository.save(any(Bus::class.java))).thenReturn(savedBus)

        val result = busService.createBus(busDTO)

        assertNotNull(result)
        assertEquals(1L, result.id)
        assertEquals(busDTO.busNumber, result.busNumber)

    }

    @Test
    fun `createBus should throw IllegalArgumentException when bus already exists`() {

        val busDTO = BusDTO(null, "TN01", "MM Travels", "Red Bus", 50, 40, "Chennai", "Coimbatore")
        val existingBus = Bus(1L, "TN01", "MM Travels", "Red Bus", 50, 40, "Chennai", "Coimbatore")

        `when`(busRepository.findByBusNumber(busDTO.busNumber)).thenReturn(Optional.of(existingBus))

        assertThrows<IllegalArgumentException> {
            busService.createBus(busDTO)
        }

    }

    @Test
    fun `getBusByBusNumber should return BusDTO when bus is found`() {

        val bus = Bus(1L, "TN01", "MM Travels", "Red Bus", 50, 40, "Chennai", "Coimbatore")

        `when`(busRepository.findByBusNumber("TN01")).thenReturn(Optional.of(bus))

        val result = busService.getBusByBusNumber("TN01")

        assertNotNull(result)
        assertEquals(bus.id, result.id)
        assertEquals(bus.busNumber, result.busNumber)

    }

    @Test
    fun `getBusByBusNumber should throw BusNotFoundException when bus is not found`() {

        `when`(busRepository.findByBusNumber("TN01")).thenReturn(Optional.empty())

        assertThrows<BusNotFoundException> {
            busService.getBusByBusNumber("TN01")
        }

    }

    @Test
    fun `getAllBuses should return list of BusDTOs`() {

        val busList = listOf(
            Bus(1L, "TN01", "MM Travels", "RedBus", 50, 40, "Chennai", "Coimbatore"),
            Bus(2L, "TN02", "ABC Travels", "MakeMyTrip", 40, 30, "Bangalore", "Kochi")
        )
        `when`(busRepository.findAll()).thenReturn(busList)

        val result = busService.getAllBuses()

        assertEquals(2, result.size)

    }

    @Test
    fun `updateBusDetails should update and return BusDTO when bus is found`() {

        val bus = Bus(1L, "TN01", "MM Travels", "Red Bus", 50, 40, "Chennai", "Coimbatore")
        val updateBusDTO = UpdateBusDTO("SS Travels", null, 60, null, null, null)

        `when`(busRepository.findByBusNumber("TN01")).thenReturn(Optional.of(bus))
        `when`(busRepository.save(bus)).thenReturn(bus)

        val result = busService.updateBusDetails(updateBusDTO, "TN01")

        assertEquals("SS Travels", result.busName)

    }

    @Test
    fun `updateBusDetails should throw BusNotFoundException when bus is not found`() {

        val updateBusDTO = UpdateBusDTO("SS Travels", null, null, null, null, null)

        `when`(busRepository.findByBusNumber("TN01")).thenReturn(Optional.empty())

        assertThrows<BusNotFoundException> {
            busService.updateBusDetails(updateBusDTO, "TN01")
        }

    }
}
