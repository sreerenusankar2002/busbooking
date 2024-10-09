package com.example.bus.service

import com.example.bus.dto.BookingDTO
import com.example.bus.dto.BusDTO
import com.example.bus.entity.Booking
import com.example.bus.repository.BookingRepository
import com.example.bus.repository.BusRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BusSearchService(val bookingRepository: BookingRepository, val busRepository: BusRepository) {
    fun bookBus(bookingDTO: BookingDTO): ResponseEntity<String> {
        val bus = busRepository.findByBusNumber(bookingDTO.busNumber)

        return if (bus.isPresent && bus.get().availableSeats!! >= bookingDTO.totalSeats) {
            val busEntity = bus.get()

            // Update available seats safely handling nullability
            val updatedSeats = (busEntity.availableSeats ?: 0) - bookingDTO.totalSeats
            busEntity.availableSeats = updatedSeats

            // Save the updated bus entity
            busRepository.save(busEntity)

            // Create and save the booking entity
            val booking = Booking(
                busNumber = bookingDTO.busNumber,
                bookingDate = bookingDTO.bookingDate,
                source = bookingDTO.source,
                destination = bookingDTO.destination,
                totalSeats = bookingDTO.totalSeats,
                bookingStatus = "Confirmed"
            )
            bookingRepository.save(booking)

            // Return a success response
            ResponseEntity.ok("Booking Confirmed")
        }  else {
            // Return an error response if there are not enough available seats
            ResponseEntity.status(400).body("Insufficient Seats")
        }
    }

    fun findBySourceAndDestination(source: String, destination: String): List<BusDTO> {
        return busRepository.findBySourceAndDestination(source, destination)
    }


}
