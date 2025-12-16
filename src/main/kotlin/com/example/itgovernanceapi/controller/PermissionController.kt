package com.example.itgovernanceapi.controller

import com.example.itgovernanceapi.dto.PermissionRequestDto
import com.example.itgovernanceapi.dto.PermissionResponseDto
import com.example.itgovernanceapi.entity.AccountType
import com.example.itgovernanceapi.service.PermissionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/permissions")
@Tag(name = "Permission Management", description = "APIs for managing permissions")
class PermissionController(private val permissionService: PermissionService) {

    @GetMapping
    @Operation(summary = "Get all permissions")
    fun getAllPermissions(): ResponseEntity<List<PermissionResponseDto>> {
        val permissions = permissionService.getAllPermissions()
        return ResponseEntity.ok(permissions)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get permission by ID")
    fun getPermissionById(@PathVariable id: UUID): ResponseEntity<PermissionResponseDto> {
        val permission = permissionService.getPermissionById(id)
        return if (permission != null) {
            ResponseEntity.ok(permission)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/account-type/{accountType}")
    @Operation(summary = "Get permissions by account type")
    fun getPermissionsByAccountType(@PathVariable accountType: AccountType): ResponseEntity<List<PermissionResponseDto>> {
        val permissions = permissionService.getPermissionsByAccountType(accountType)
        return ResponseEntity.ok(permissions)
    }

    @PostMapping
    @Operation(summary = "Create a new permission")
    fun createPermission(@Valid @RequestBody request: PermissionRequestDto): ResponseEntity<PermissionResponseDto> {
        val permission = permissionService.createPermission(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(permission)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing permission")
    fun updatePermission(@PathVariable id: UUID, @Valid @RequestBody request: PermissionRequestDto): ResponseEntity<PermissionResponseDto> {
        val permission = permissionService.updatePermission(id, request)
        return if (permission != null) {
            ResponseEntity.ok(permission)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a permission")
    fun deletePermission(@PathVariable id: UUID): ResponseEntity<Void> {
        val deleted = permissionService.deletePermission(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}