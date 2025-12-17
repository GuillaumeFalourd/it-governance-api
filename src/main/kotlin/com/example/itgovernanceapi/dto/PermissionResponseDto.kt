package com.example.itgovernanceapi.dto

import com.example.itgovernanceapi.entity.AccountType
import java.util.*

data class PermissionResponseDto(
    val id: UUID,
    val accountId: UUID,
    val accountType: AccountType,
    val accountIdentifier: String,
    val name: String,
    val description: String?
)