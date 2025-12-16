package com.example.itgovernanceapi.repository

import com.example.itgovernanceapi.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountRepository : JpaRepository<Account, UUID>