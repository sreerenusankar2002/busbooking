package com.example.bus.service

import com.example.bus.dto.BusDTO
import com.example.bus.dto.UpdateBusDTO
import com.example.bus.entity.Bus
import com.example.bus.exception.BusNotFoundException
import com.example.bus.repository.BusRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BusService(val busRepository: BusRepository) {

    fun createBus(busDTO: BusDTO): BusDTO {
        // Check if a bus with the same bus number already exists
        val existingBus = busRepository.findByBusNumber(busDTO.busNumber)
        if (existingBus.isPresent) {
            throw IllegalArgumentException("Bus with the number ${busDTO.busNumber} already exists.")
        }

        val busEntity = Bus(
            null,
            busDTO.busNumber,
            busDTO.busName,
            busDTO.travelCompany,
            busDTO.capacity,
            busDTO.availableSeats,
            busDTO.source,
            busDTO.destination
        )
        val savedBus = busRepository.save(busEntity)
        return BusDTO(
            savedBus.id,
            savedBus.busNumber,
            savedBus.busName,
            savedBus.travelCompany,
            savedBus.capacity,
            savedBus.availableSeats,
            savedBus.source,
            savedBus.destination
        )
    }

    fun getBusByBusNumber(busNumber: String): BusDTO {
        val bus = busRepository.findByBusNumber(busNumber)
            .orElseThrow { BusNotFoundException("Bus with bus number $busNumber not found") }

        return BusDTO(
            bus.id,
            bus.busNumber,
            bus.busName,
            bus.travelCompany,
            bus.capacity,
            bus.availableSeats,
            bus.source,
            bus.destination
        )
    }


    fun getAllBuses(): List<BusDTO> {
        return busRepository.findAll()
            .map {
                BusDTO(
                    it.id,
                    it.busNumber,
                    it.busName,
                    it.travelCompany,
                    it.capacity,
                    it.availableSeats,
                    it.source,
                    it.destination
                )
            }
    }

    fun updateBusDetails(updateBusDTO: UpdateBusDTO, busNumber: String): BusDTO {
        val existingBus = busRepository.findByBusNumber(busNumber)
            .orElseThrow { BusNotFoundException("No bus found for the given busNumber: $busNumber") }

        // Update only the non-null fields from UpdateBusDTO
        updateBusDTO.busName?.let { existingBus.busName = it }
        updateBusDTO.travelCompany?.let { existingBus.travelCompany = it }
        updateBusDTO.capacity?.let { existingBus.capacity = it }
        updateBusDTO.availableSeats?.let { existingBus.availableSeats = it }
        updateBusDTO.source?.let { existingBus.source = it }
        updateBusDTO.destination?.let { existingBus.destination = it }

        val updatedBus = busRepository.save(existingBus)

        return BusDTO(
            updatedBus.id,
            updatedBus.busNumber,
            updatedBus.busName,
            updatedBus.travelCompany,
            updatedBus.capacity,
            updatedBus.availableSeats,
            updatedBus.source,
            updatedBus.destination
        )
    }
}
