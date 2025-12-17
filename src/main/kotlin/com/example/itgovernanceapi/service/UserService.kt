package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.UserRequestDto
import com.example.itgovernanceapi.dto.UserResponseDto
import com.example.itgovernanceapi.entity.User
import com.example.itgovernanceapi.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<UserResponseDto> {
        return userRepository.findAll().map { it.toResponseDto() }
    }

    fun getUserById(id: UUID): UserResponseDto? {
        return userRepository.findById(id).orElse(null)?.toResponseDto()
    }

    fun getUserByEmail(email: String): UserResponseDto? {
        return userRepository.findByCompanyEmail(email)?.toResponseDto()
    }

    @Transactional
    fun createUser(request: UserRequestDto): UserResponseDto {
        if (userRepository.findByCompanyEmail(request.companyEmail) != null) {
            throw IllegalArgumentException("User with email ${request.companyEmail} already exists")
        }
        val user = User(
            name = request.name,
            companyEmail = request.companyEmail,
            githubAccount = request.githubAccount,
            githubOrganizations = request.githubOrganizations,
            githubTeamsPerOrganization = request.githubTeamsPerOrganization,
            awsOrganizationUnits = request.awsOrganizationUnits,
            awsAccounts = request.awsAccounts,
            awsRolesPerAccount = request.awsRolesPerAccount
        )
        return userRepository.save(user).toResponseDto()
    }

    @Transactional
    fun updateUser(id: UUID, request: UserRequestDto): UserResponseDto? {
        val existing = userRepository.findById(id).orElse(null) ?: return null
        val updated = existing.copy(
            name = request.name,
            companyEmail = request.companyEmail,
            githubAccount = request.githubAccount,
            githubOrganizations = request.githubOrganizations,
            githubTeamsPerOrganization = request.githubTeamsPerOrganization,
            awsOrganizationUnits = request.awsOrganizationUnits,
            awsAccounts = request.awsAccounts,
            awsRolesPerAccount = request.awsRolesPerAccount
        )
        return userRepository.save(updated).toResponseDto()
    }

    @Transactional
    fun deleteUser(id: UUID): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    private fun User.toResponseDto(): UserResponseDto {
        return UserResponseDto(
            id = this.id ?: throw IllegalStateException("User ID should not be null after saving"),
            name = this.name,
            companyEmail = this.companyEmail,
            githubAccount = this.githubAccount,
            githubOrganizations = this.githubOrganizations,
            githubTeamsPerOrganization = this.githubTeamsPerOrganization,
            awsOrganizationUnits = this.awsOrganizationUnits,
            awsAccounts = this.awsAccounts,
            awsRolesPerAccount = this.awsRolesPerAccount
        )
    }
}