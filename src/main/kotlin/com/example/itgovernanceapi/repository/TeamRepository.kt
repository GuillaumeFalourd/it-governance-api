package com.example.itgovernanceapi.repository

import com.example.itgovernanceapi.entity.Team
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TeamRepository : JpaRepository<Team, UUID>