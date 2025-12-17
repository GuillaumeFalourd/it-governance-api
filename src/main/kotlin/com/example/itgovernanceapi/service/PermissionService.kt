package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.PermissionRequestDto
import com.example.itgovernanceapi.dto.PermissionResponseDto
import com.example.itgovernanceapi.entity.AccountType
import com.example.itgovernanceapi.entity.Permission
import com.example.itgovernanceapi.repository.PermissionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PermissionService(private val permissionRepository: PermissionRepository) {

    fun getAllPermissions(): List<PermissionResponseDto> {
        return permissionRepository.findAll().map { it.toResponseDto() }
    }

    fun getPermissionById(id: UUID): PermissionResponseDto? {
        return permissionRepository.findById(id).orElse(null)?.toResponseDto()
    }

    fun getPermissionsByAccountType(accountType: AccountType): List<PermissionResponseDto> {
        return permissionRepository.findByAccountType(accountType).map { it.toResponseDto() }
    }

    @Transactional
    fun createPermission(request: PermissionRequestDto): PermissionResponseDto {
        val permission = Permission(
            accountType = request.accountType,
            name = request.name,
            description = request.description
        )
        return permissionRepository.save(permission).toResponseDto()
    }

    @Transactional
    fun updatePermission(id: UUID, request: PermissionRequestDto): PermissionResponseDto? {
        val existing = permissionRepository.findById(id).orElse(null) ?: return null
        val updated = existing.copy(
            accountType = request.accountType,
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
        accountType = accountType,
        name = name,
        description = description
    )
}