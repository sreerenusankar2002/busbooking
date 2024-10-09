package com.example.bus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BusBookingSystemApplication

fun main(args: Array<String>) {
	runApplication<BusBookingSystemApplication>(*args)
}
