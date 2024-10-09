package com.example.bus.dto

data class UpdateBusDTO(
    val busName: String? = null,
    val travelCompany: String? = null,
    var capacity: Int? = null,
    var availableSeats: Int? = null,
    var source: String? = null,
    var destination: String? = null
)
