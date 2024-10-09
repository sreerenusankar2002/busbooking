package com.example.bus.dto

data class CustomerDTO(
    val id: Long = 0,
    val phone: String,
    val fname: String,
    val lname: String,
    val password: String
)
