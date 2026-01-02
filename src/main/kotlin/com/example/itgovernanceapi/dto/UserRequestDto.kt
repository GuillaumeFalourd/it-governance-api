package com.example.itgovernanceapi.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.*

data class UserRequestDto(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:Email(message = "Invalid email format")
    @field:NotBlank(message = "Company email is required")
    val companyEmail: String,

    @field:NotBlank(message = "Github account is required")
    val githubAccount: String,

    val teamIds: Set<UUID> = setOf()
)