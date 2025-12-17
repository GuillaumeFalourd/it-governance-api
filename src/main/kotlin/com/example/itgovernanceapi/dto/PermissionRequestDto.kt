package com.example.itgovernanceapi.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

data class PermissionRequestDto(
    @field:NotNull(message = "Account ID is required")
    val accountId: UUID,

    @field:NotBlank(message = "Name is required")
    val name: String,

    val description: String? = null
)