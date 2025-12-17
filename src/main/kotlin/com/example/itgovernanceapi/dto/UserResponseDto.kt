package com.example.itgovernanceapi.dto

import java.util.*

data class UserResponseDto(
    val id: UUID,
    val name: String,
    val companyEmail: String,
    val githubAccount: String,
    val accounts: List<AccountSummaryDto>,
    val permissions: List<PermissionSummaryDto>
)

data class AccountSummaryDto(
    val id: UUID,
    val type: String,
    val identifier: String
)

data class PermissionSummaryDto(
    val id: UUID,
    val accountId: UUID,
    val accountIdentifier: String,
    val name: String
)