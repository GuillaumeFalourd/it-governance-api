package com.example.itgovernanceapi.repository

import com.example.itgovernanceapi.entity.AccountType
import com.example.itgovernanceapi.entity.Permission
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PermissionRepository : JpaRepository<Permission, UUID> {
    fun findByAccountType(accountType: AccountType): List<Permission>
}