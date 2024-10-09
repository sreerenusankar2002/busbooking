package com.example.bus.dto

data class BusDTO(
    val id: Long? = null,
    val busNumber: String,
    val busName: String,
    val travelCompany: String,
    var capacity: Int,
    var availableSeats: Int,
    var source: String,
    var destination: String
)


