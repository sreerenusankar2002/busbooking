package com.example.bus.repository

import com.example.bus.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByPhone(phone: String): Customer?
}
