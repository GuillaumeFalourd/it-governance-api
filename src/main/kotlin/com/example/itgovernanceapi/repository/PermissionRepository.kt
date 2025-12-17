package com.example.itgovernanceapi.repository

import com.example.itgovernanceapi.entity.Permission
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PermissionRepository : JpaRepository<Permission, UUID> {
    fun findByAccountId(accountId: UUID): List<Permission>
}