package com.example.itgovernanceapi.dto

import java.util.*

data class UserResponseDto(
    val id: UUID,
    val name: String,
    val companyEmail: String,
    val githubAccount: String,
    val githubOrganizations: List<String>,
    val githubTeamsPerOrganization: Map<String, List<String>>,
    val awsOrganizationUnits: List<String>,
    val awsAccounts: List<String>,
    val awsRolesPerAccount: Map<String, List<String>>
)