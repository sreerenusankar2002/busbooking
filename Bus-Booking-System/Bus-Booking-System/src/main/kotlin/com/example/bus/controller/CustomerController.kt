package com.example.bus.controller

import com.example.bus.dto.CustomerDTO
import com.example.bus.entity.Customer
import com.example.bus.repository.CustomerRepository
import com.example.bus.security.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import com.example.bus.dto.AuthRequest

@RestController
@RequestMapping("/booking-system")
class CustomerController(
    private val customerRepository: CustomerRepository,
    private val jwtUtils: JwtUtils
) {

    private val passwordEncoder = BCryptPasswordEncoder()

    @PostMapping("/register")
    fun register(@RequestBody customerDTO: CustomerDTO): ResponseEntity<String> {
        return try {
            val hashedPassword = passwordEncoder.encode(customerDTO.password)
            val customer = Customer(
                phone = customerDTO.phone,
                fname = customerDTO.fname,
                lname = customerDTO.lname,
                password = hashedPassword
            )
            customerRepository.save(customer)
            ResponseEntity.ok("Registration successful")
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: ${ex.message}")
        }
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authRequest: AuthRequest): ResponseEntity<String> {
        val customer = customerRepository.findByPhone(authRequest.phone)

        return if (customer != null && passwordEncoder.matches(authRequest.password, customer.password)) {
            val token = jwtUtils.generateToken(customer.phone)
            ResponseEntity.ok(token)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid credentials")
        }
    }
}
