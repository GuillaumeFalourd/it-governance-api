package com.example.itgovernanceapi.controller

import com.example.itgovernanceapi.dto.UserRequestDto
import com.example.itgovernanceapi.dto.UserResponseDto
import com.example.itgovernanceapi.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs for managing users")
class UserController(private val userService: UserService) {

    @GetMapping
    @Operation(summary = "Get all users")
    fun getAllUsers(): ResponseEntity<List<UserResponseDto>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<UserResponseDto> {
        val user = userService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    fun createUser(@Valid @RequestBody request: UserRequestDto): ResponseEntity<UserResponseDto> {
        return try {
            val user = userService.createUser(request)
            ResponseEntity.status(HttpStatus.CREATED).body(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    fun updateUser(@PathVariable id: UUID, @Valid @RequestBody request: UserRequestDto): ResponseEntity<UserResponseDto> {
        val user = userService.updateUser(id, request)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        val deleted = userService.deleteUser(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}