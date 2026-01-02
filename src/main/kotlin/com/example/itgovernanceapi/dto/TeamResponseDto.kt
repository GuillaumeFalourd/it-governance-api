package com.example.itgovernanceapi.dto

import java.util.*

data class TeamResponseDto(
    val id: UUID,
    val name: String,
    val description: String?,
    val accounts: List<AccountSummaryDto>,
    val permissions: List<PermissionSummaryDto>
)