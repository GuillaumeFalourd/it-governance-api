package com.example.itgovernanceapi.dto

import com.example.itgovernanceapi.entity.AccountType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AccountRequestDto(
    @field:NotNull(message = "Type is required")
    val type: AccountType,

    @field:NotBlank(message = "Identifier is required")
    val identifier: String,

    val description: String? = null
)