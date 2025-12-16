package com.example.itgovernanceapi.repository

import com.example.itgovernanceapi.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByCompanyEmail(email: String): User?
}