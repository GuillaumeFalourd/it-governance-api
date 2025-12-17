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

    @ElementCollection
    @CollectionTable(name = "user_github_organizations", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "organization")
    val githubOrganizations: List<String> = listOf(),

    @ElementCollection
    @CollectionTable(name = "user_github_teams", joinColumns = [JoinColumn(name = "user_id")])
    @MapKeyColumn(name = "organization")
    @Column(name = "teams")
    val githubTeamsPerOrganization: Map<String, List<String>> = mapOf(),

    @ElementCollection
    @CollectionTable(name = "user_aws_organization_units", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "unit")
    val awsOrganizationUnits: List<String> = listOf(),

    @ElementCollection
    @CollectionTable(name = "user_aws_accounts", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "account")
    val awsAccounts: List<String> = listOf(),

    @ElementCollection
    @CollectionTable(name = "user_aws_roles", joinColumns = [JoinColumn(name = "user_id")])
    @MapKeyColumn(name = "account")
    @Column(name = "roles")
    val awsRolesPerAccount: Map<String, List<String>> = mapOf()
)