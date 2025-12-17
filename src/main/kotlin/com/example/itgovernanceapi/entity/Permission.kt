package com.example.itgovernanceapi.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "permissions")
data class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    val account: Account,

    @Column(nullable = false)
    val name: String, // e.g., "READ", "WRITE", "ADMIN"

    @Column
    val description: String? = null
)