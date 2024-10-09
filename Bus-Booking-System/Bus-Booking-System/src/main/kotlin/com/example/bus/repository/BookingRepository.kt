package com.example.bus.repository

import com.example.bus.entity.Booking
import org.springframework.data.jpa.repository.JpaRepository

interface BookingRepository : JpaRepository<Booking, Long>
