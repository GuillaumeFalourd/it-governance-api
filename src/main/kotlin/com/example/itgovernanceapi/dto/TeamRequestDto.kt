package com.example.itgovernanceapi.dto

import jakarta.validation.constraints.NotBlank
import java.util.*

data class TeamRequestDto(
    @field:NotBlank(message = "Name is required")
    val name: String,

    val description: String? = null,

    val accountIds: Set<UUID> = setOf(),

    val permissionIds: Set<UUID> = setOf()
)