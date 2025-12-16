package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.UserRequestDto
import com.example.itgovernanceapi.entity.User
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

    @Test
    fun `should create user successfully`() {
        val request = UserRequestDto(
            name = "John Doe",
            companyEmail = "john.doe@example.com",
            githubAccount = "johndoe"
        )

        val savedUser = User(
            id = UUID.randomUUID(),
            name = request.name,
            companyEmail = request.companyEmail,
            githubAccount = request.githubAccount
        )

        `when`(userRepository.findByCompanyEmail(request.companyEmail)).thenReturn(null)
        `when`(userRepository.save(any(User::class.java))).thenReturn(savedUser)

        val response = userService.createUser(request)

        assertNotNull(response)
        assertEquals(request.name, response.name)
        assertEquals(request.companyEmail, response.companyEmail)
        verify(userRepository).save(any(User::class.java))
    }
}