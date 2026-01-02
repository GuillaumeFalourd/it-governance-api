package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.*
import com.example.itgovernanceapi.entity.User
import com.example.itgovernanceapi.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val teamRepository: com.example.itgovernanceapi.repository.TeamRepository
) {

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

        val teams = if (request.teamIds.isNotEmpty()) {
            teamRepository.findAllById(request.teamIds).toSet()
        } else {
            setOf()
        }

        val user = User(
            name = request.name,
            companyEmail = request.companyEmail,
            githubAccount = request.githubAccount,
            teams = teams
        )
        return userRepository.save(user).toResponseDto()
    }

    @Transactional
    fun updateUser(id: UUID, request: UserRequestDto): UserResponseDto? {
        val existing = userRepository.findById(id).orElse(null) ?: return null
        
        val teams = if (request.teamIds.isNotEmpty()) {
            teamRepository.findAllById(request.teamIds).toSet()
        } else {
            existing.teams
        }

        val updated = existing.copy(
            name = request.name,
            companyEmail = request.companyEmail,
            githubAccount = request.githubAccount,
            teams = teams
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
        val allAccounts = teams.flatMap { it.accounts }.distinctBy { it.id }
        val allPermissions = teams.flatMap { it.permissions }.distinctBy { it.id }

        return UserResponseDto(
            id = this.id ?: throw IllegalStateException("User ID should not be null after saving"),
            name = this.name,
            companyEmail = this.companyEmail,
            githubAccount = this.githubAccount,
            teams = this.teams.map { TeamSummaryDto(it.id!!, it.name) },
            accounts = allAccounts.map { AccountSummaryDto(it.id!!, it.type.name, it.identifier) },
            permissions = allPermissions.map { PermissionSummaryDto(it.id!!, it.account.id!!, it.account.identifier, it.name) }
        )
    }
}