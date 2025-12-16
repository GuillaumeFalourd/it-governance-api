package com.example.itgovernanceapi.dto

import com.example.itgovernanceapi.entity.AccountType
import java.util.*

data class AccountResponseDto(
    val id: UUID,
    val type: AccountType,
    val identifier: String,
    val description: String?
)