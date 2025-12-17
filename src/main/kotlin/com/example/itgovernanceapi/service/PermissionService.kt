package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.PermissionRequestDto
import com.example.itgovernanceapi.dto.PermissionResponseDto
import com.example.itgovernanceapi.entity.Permission
import com.example.itgovernanceapi.repository.PermissionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PermissionService(
    private val permissionRepository: PermissionRepository,
    private val accountRepository: com.example.itgovernanceapi.repository.AccountRepository
) {

    fun getAllPermissions(): List<PermissionResponseDto> {
        return permissionRepository.findAll().map { it.toResponseDto() }
    }

    fun getPermissionById(id: UUID): PermissionResponseDto? {
        return permissionRepository.findById(id).orElse(null)?.toResponseDto()
    }

    @Transactional
    fun createPermission(request: PermissionRequestDto): PermissionResponseDto {
        val account = accountRepository.findById(request.accountId).orElse(null)
            ?: throw IllegalArgumentException("Account not found with id: ${request.accountId}")

        val permission = Permission(
            account = account,
            name = request.name,
            description = request.description
        )
        return permissionRepository.save(permission).toResponseDto()
    }

    @Transactional
    fun updatePermission(id: UUID, request: PermissionRequestDto): PermissionResponseDto? {
        val existing = permissionRepository.findById(id).orElse(null) ?: return null
        
        val account = if (request.accountId != existing.account.id) {
            accountRepository.findById(request.accountId).orElse(null) ?: return null
        } else {
            existing.account
        }
        
        val updated = existing.copy(
            account = account,
            name = request.name,
            description = request.description
        )
        return permissionRepository.save(updated).toResponseDto()
    }

    @Transactional
    fun deletePermission(id: UUID): Boolean {
        return if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    private fun Permission.toResponseDto() = PermissionResponseDto(
        id = id ?: throw IllegalStateException("Permission ID should not be null after saving"),
        accountId = account.id!!,
        accountType = account.type,
        accountIdentifier = account.identifier,
        name = name,
        description = description
    )
}