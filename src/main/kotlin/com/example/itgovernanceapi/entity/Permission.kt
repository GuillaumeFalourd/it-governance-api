package com.example.itgovernanceapi.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "permissions")
data class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val accountType: AccountType,

    @Column(nullable = false)
    val name: String, // e.g., "READ", "WRITE", "ADMIN"

    @Column
    val description: String? = null
)