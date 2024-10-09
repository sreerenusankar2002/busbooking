package com.example.bus.repository
import com.example.bus.dto.BusDTO
import com.example.bus.entity.Bus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface BusRepository : JpaRepository<Bus, Long> {
    fun findBySourceAndDestination(source: String, destination: String): List<BusDTO>
    fun findByBusNumber(busNumber: String): Optional<Bus>
}

