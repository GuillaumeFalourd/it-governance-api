package com.example.itgovernanceapi.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserRequestDto(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:Email(message = "Invalid email format")
    @field:NotBlank(message = "Company email is required")
    val companyEmail: String,

    @field:NotBlank(message = "Github account is required")
    val githubAccount: String,

    val githubOrganizations: List<String> = listOf(),

    val githubTeamsPerOrganization: Map<String, List<String>> = mapOf(),

    val awsOrganizationUnits: List<String> = listOf(),

    val awsAccounts: List<String> = listOf(),

    val awsRolesPerAccount: Map<String, List<String>> = mapOf()
)