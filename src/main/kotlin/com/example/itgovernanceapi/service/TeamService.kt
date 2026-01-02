package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.TeamRequestDto
import com.example.itgovernanceapi.dto.TeamResponseDto
import com.example.itgovernanceapi.entity.Team
import com.example.itgovernanceapi.repository.TeamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TeamService(
    private val teamRepository: TeamRepository,
    private val accountRepository: com.example.itgovernanceapi.repository.AccountRepository,
    private val permissionRepository: com.example.itgovernanceapi.repository.PermissionRepository
) {

    fun getAllTeams(): List<TeamResponseDto> {
        return teamRepository.findAll().map { it.toResponseDto() }
    }

    fun getTeamById(id: UUID): TeamResponseDto? {
        return teamRepository.findById(id).orElse(null)?.toResponseDto()
    }

    @Transactional
    fun createTeam(request: TeamRequestDto): TeamResponseDto {
        val accounts = if (request.accountIds.isNotEmpty()) {
            accountRepository.findAllById(request.accountIds).toSet()
        } else {
            setOf()
        }

        val permissions = if (request.permissionIds.isNotEmpty()) {
            permissionRepository.findAllById(request.permissionIds).toSet()
        } else {
            setOf()
        }

        val team = Team(
            name = request.name,
            description = request.description,
            accounts = accounts.toMutableSet(),
            permissions = permissions.toMutableSet()
        )
        return teamRepository.save(team).toResponseDto()
    }

    @Transactional
    fun updateTeam(id: UUID, request: TeamRequestDto): TeamResponseDto? {
        val existing = teamRepository.findById(id).orElse(null) ?: return null

        val accounts = if (request.accountIds.isNotEmpty()) {
            accountRepository.findAllById(request.accountIds).toMutableSet()
        } else {
            existing.accounts
        }

        val permissions = if (request.permissionIds.isNotEmpty()) {
            permissionRepository.findAllById(request.permissionIds).toMutableSet()
        } else {
            existing.permissions
        }

        val updated = existing.copy(
            name = request.name,
            description = request.description,
            accounts = accounts,
            permissions = permissions
        )
        return teamRepository.save(updated).toResponseDto()
    }

    @Transactional
    fun deleteTeam(id: UUID): Boolean {
        return if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    @Transactional
    fun addAccountToTeam(teamId: UUID, accountId: UUID): TeamResponseDto? {
        val team = teamRepository.findById(teamId).orElse(null) ?: return null
        val account = accountRepository.findById(accountId).orElse(null) ?: return null
        team.accounts.add(account)
        return teamRepository.save(team).toResponseDto()
    }

    @Transactional
    fun removeAccountFromTeam(teamId: UUID, accountId: UUID): TeamResponseDto? {
        val team = teamRepository.findById(teamId).orElse(null) ?: return null
        val accountToRemove = team.accounts.find { it.id == accountId }
        accountToRemove?.let { team.accounts.remove(it) }
        return teamRepository.save(team).toResponseDto()
    }

    @Transactional
    fun addPermissionToTeam(teamId: UUID, permissionId: UUID): TeamResponseDto? {
        val team = teamRepository.findById(teamId).orElse(null) ?: return null
        val permission = permissionRepository.findById(permissionId).orElse(null) ?: return null
        team.permissions.add(permission)
        return teamRepository.save(team).toResponseDto()
    }

    @Transactional
    fun removePermissionFromTeam(teamId: UUID, permissionId: UUID): TeamResponseDto? {
        val team = teamRepository.findById(teamId).orElse(null) ?: return null
        val permissionToRemove = team.permissions.find { it.id == permissionId }
        permissionToRemove?.let { team.permissions.remove(it) }
        return teamRepository.save(team).toResponseDto()
    }

    private fun Team.toResponseDto() = TeamResponseDto(
        id = id ?: throw IllegalStateException("Team ID should not be null after saving"),
        name = name,
        description = description,
        accounts = accounts.map { com.example.itgovernanceapi.dto.AccountSummaryDto(it.id!!, it.type.name, it.identifier) },
        permissions = permissions.map { com.example.itgovernanceapi.dto.PermissionSummaryDto(it.id!!, it.account.id!!, it.account.identifier, it.name) }
    )
}