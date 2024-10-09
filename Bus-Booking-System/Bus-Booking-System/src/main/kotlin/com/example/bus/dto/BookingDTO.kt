package com.example.bus.dto

import java.time.LocalDate

data class BookingDTO(
    val bookingNumber: Long = 0,
    val busNumber: String,
    val bookingDate: LocalDate,
    val source: String,
    val destination: String,
    val totalSeats: Int,
    val bookingStatus: String = "CONFIRMED"
)
