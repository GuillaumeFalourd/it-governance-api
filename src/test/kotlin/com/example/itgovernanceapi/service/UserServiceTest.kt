package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.UserRequestDto
import com.example.itgovernanceapi.entity.Account
import com.example.itgovernanceapi.entity.AccountType
import com.example.itgovernanceapi.entity.Permission
import com.example.itgovernanceapi.entity.Team
import com.example.itgovernanceapi.entity.User
import com.example.itgovernanceapi.repository.AccountRepository
import com.example.itgovernanceapi.repository.TeamRepository
import com.example.itgovernanceapi.repository.UserRepository
import com.example.itgovernanceapi.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest(properties = ["spring.profiles.active=test"])
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var teamRepository: TeamRepository

    @Test
    fun `should create user successfully`() {
        val teamId = UUID.randomUUID()
        val accountId = UUID.randomUUID()
        val permissionId = UUID.randomUUID()
        val testAccount = Account(
            id = accountId,
            type = AccountType.AWS,
            identifier = "123456789",
            description = "Test account"
        )
        val testPermission = Permission(
            id = permissionId,
            account = testAccount,
            name = "READ",
            description = "Read permission"
        )
        val testTeam = Team(
            id = teamId,
            name = "Test Team",
            description = "Test team",
            accounts = mutableSetOf(testAccount),
            permissions = mutableSetOf(testPermission)
        )

        val request = UserRequestDto(
            name = "John Doe",
            companyEmail = "john.doe@example.com",
            githubAccount = "johndoe",
            teamIds = setOf(teamId)
        )

        val savedUser = User(
            id = UUID.randomUUID(),
            name = request.name,
            companyEmail = request.companyEmail,
            githubAccount = request.githubAccount,
            teams = setOf(testTeam)
        )

        `when`(userRepository.findByCompanyEmail(request.companyEmail)).thenReturn(null)
        `when`(teamRepository.findAllById(request.teamIds)).thenReturn(listOf(testTeam))
        `when`(userRepository.save(any(User::class.java))).thenReturn(savedUser)

        val response = userService.createUser(request)

        assertNotNull(response)
        assertEquals(request.name, response.name)
        assertEquals(request.companyEmail, response.companyEmail)
        verify(userRepository).save(any(User::class.java))
        verify(teamRepository).findAllById(request.teamIds)
    }
}