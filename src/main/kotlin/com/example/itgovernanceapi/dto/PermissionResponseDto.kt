package com.example.itgovernanceapi.dto

import com.example.itgovernanceapi.entity.AccountType
import java.util.*

data class PermissionResponseDto(
    val id: UUID,
    val accountType: AccountType,
    val name: String,
    val description: String?
)