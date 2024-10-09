package com.example.bus.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Bus(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val busNumber: String,
    var busName: String,
    var travelCompany: String,
    var capacity: Int,
    var availableSeats: Int,
    var source: String,
    var destination: String
)


