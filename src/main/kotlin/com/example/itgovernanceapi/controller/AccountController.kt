package com.example.itgovernanceapi.controller

import com.example.itgovernanceapi.dto.AccountRequestDto
import com.example.itgovernanceapi.dto.AccountResponseDto
import com.example.itgovernanceapi.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account Management", description = "APIs for managing accounts")
class AccountController(private val accountService: AccountService) {

    @GetMapping
    @Operation(summary = "Get all accounts")
    fun getAllAccounts(): ResponseEntity<List<AccountResponseDto>> {
        val accounts = accountService.getAllAccounts()
        return ResponseEntity.ok(accounts)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID")
    fun getAccountById(@PathVariable id: UUID): ResponseEntity<AccountResponseDto> {
        val account = accountService.getAccountById(id)
        return if (account != null) {
            ResponseEntity.ok(account)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    @Operation(summary = "Create a new account")
    fun createAccount(@Valid @RequestBody request: AccountRequestDto): ResponseEntity<AccountResponseDto> {
        val account = accountService.createAccount(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(account)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account")
    fun updateAccount(@PathVariable id: UUID, @Valid @RequestBody request: AccountRequestDto): ResponseEntity<AccountResponseDto> {
        val account = accountService.updateAccount(id, request)
        return if (account != null) {
            ResponseEntity.ok(account)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account")
    fun deleteAccount(@PathVariable id: UUID): ResponseEntity<Void> {
        val deleted = accountService.deleteAccount(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}