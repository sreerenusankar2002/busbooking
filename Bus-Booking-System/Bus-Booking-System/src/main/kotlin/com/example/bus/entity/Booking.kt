package com.example.bus.entity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val bookingNumber: Long = 0,
    val busNumber: String,
    val bookingDate: LocalDate,
    val source: String,
    val destination: String,
    val totalSeats: Int,
    val bookingStatus: String = "CONFIRMED"
)
