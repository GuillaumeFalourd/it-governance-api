package com.example.itgovernanceapi.dto

import com.example.itgovernanceapi.entity.AccountType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PermissionRequestDto(
    @field:NotNull(message = "Account type is required")
    val accountType: AccountType,

    @field:NotBlank(message = "Name is required")
    val name: String,

    val description: String? = null
)