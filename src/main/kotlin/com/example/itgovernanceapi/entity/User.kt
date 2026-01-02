package com.example.itgovernanceapi.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false)
    val name: String,

    @Column(unique = true, nullable = false)
    val companyEmail: String,

    @Column(nullable = false)
    val githubAccount: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_teams",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "team_id")]
    )
    val teams: Set<Team> = setOf()
)