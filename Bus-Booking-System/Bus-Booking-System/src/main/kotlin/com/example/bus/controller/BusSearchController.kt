package com.example.bus.controller

import com.example.bus.dto.BookingDTO
import com.example.bus.dto.BusDTO
import com.example.bus.service.BusSearchService
import com.example.bus.service.BusService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/booking-system")
class BusSearchController(val busService: BusService,val busSearchService: BusSearchService)
{
    @GetMapping("/search")
    fun searchBus(@RequestParam source: String, @RequestParam destination: String): List<BusDTO> {
        return busSearchService.findBySourceAndDestination(source, destination)
    }

    @PostMapping("/book")
    fun bookBus(@RequestBody bookingDTO: BookingDTO): ResponseEntity<String> {
        return busSearchService.bookBus(bookingDTO)
    }
}