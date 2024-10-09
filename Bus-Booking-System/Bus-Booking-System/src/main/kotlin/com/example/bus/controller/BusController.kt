package com.example.bus.controller
import com.example.bus.dto.BusDTO
import com.example.bus.dto.UpdateBusDTO
import com.example.bus.service.BusService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/booking-system/bus")
class BusController(val busService: BusService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBus(@RequestBody busDTO: BusDTO): BusDTO {
        return busService.createBus(busDTO)
    }

    @GetMapping("/{busNumber}")
    fun getBusByBusNumber(@PathVariable busNumber: String): ResponseEntity<BusDTO> {
        val busDTO = busService.getBusByBusNumber(busNumber)
        return ResponseEntity.ok(busDTO)
    }

    @GetMapping
    fun getAllBuses(): List<BusDTO> = busService.getAllBuses()


    @PutMapping("/{busNumber}")
    fun updateBusDetails(
        @RequestBody updateBusDTO: UpdateBusDTO,
        @PathVariable("busNumber") busNumber: String
    ): BusDTO {
        return busService.updateBusDetails(updateBusDTO, busNumber)
    }


}
