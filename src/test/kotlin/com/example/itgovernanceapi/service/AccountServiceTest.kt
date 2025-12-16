package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.AccountRequestDto
import com.example.itgovernanceapi.entity.Account
import com.example.itgovernanceapi.entity.AccountType
import com.example.itgovernanceapi.repository.AccountRepository
import com.example.itgovernanceapi.service.AccountService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest(properties = ["spring.profiles.active=test"])
class AccountServiceTest {

    @Autowired
    private lateinit var accountService: AccountService

    @MockBean
    private lateinit var accountRepository: AccountRepository

    @Test
    fun `should create account successfully`() {
        val request = AccountRequestDto(
            type = AccountType.AWS,
            identifier = "123456789012",
            description = "Production AWS Account"
        )

        val savedAccount = Account(
            id = UUID.randomUUID(),
            type = request.type,
            identifier = request.identifier,
            description = request.description
        )

        `when`(accountRepository.save(any(Account::class.java))).thenReturn(savedAccount)

        val response = accountService.createAccount(request)

        assertNotNull(response)
        assertEquals(request.type, response.type)
        assertEquals(request.identifier, response.identifier)
        assertEquals(request.description, response.description)
        verify(accountRepository).save(any(Account::class.java))
    }

    @Test
    fun `should get account by id successfully`() {
        val accountId = UUID.randomUUID()
        val account = Account(
            id = accountId,
            type = AccountType.GITHUB,
            identifier = "my-org",
            description = "Organization account"
        )

        `when`(accountRepository.findById(accountId)).thenReturn(Optional.of(account))

        val response = accountService.getAccountById(accountId)

        assertNotNull(response)
        assertEquals(accountId, response?.id)
        assertEquals(AccountType.GITHUB, response?.type)
        assertEquals("my-org", response?.identifier)
        verify(accountRepository).findById(accountId)
    }

    @Test
    fun `should return null when account not found by id`() {
        val accountId = UUID.randomUUID()

        `when`(accountRepository.findById(accountId)).thenReturn(Optional.empty())

        val response = accountService.getAccountById(accountId)

        assertEquals(null, response)
        verify(accountRepository).findById(accountId)
    }

    @Test
    fun `should update account successfully`() {
        val accountId = UUID.randomUUID()
        val existingAccount = Account(
            id = accountId,
            type = AccountType.AWS,
            identifier = "123456789012",
            description = "Old description"
        )

        val request = AccountRequestDto(
            type = AccountType.AWS,
            identifier = "987654321098",
            description = "Updated description"
        )

        val updatedAccount = existingAccount.copy(
            type = request.type,
            identifier = request.identifier,
            description = request.description
        )

        `when`(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount))
        `when`(accountRepository.save(any(Account::class.java))).thenReturn(updatedAccount)

        val response = accountService.updateAccount(accountId, request)

        assertNotNull(response)
        assertEquals(accountId, response?.id)
        assertEquals(request.identifier, response?.identifier)
        assertEquals(request.description, response?.description)
        verify(accountRepository).findById(accountId)
        verify(accountRepository).save(any(Account::class.java))
    }

    @Test
    fun `should return null when updating non-existent account`() {
        val accountId = UUID.randomUUID()
        val request = AccountRequestDto(
            type = AccountType.AWS,
            identifier = "123456789012",
            description = "Test account"
        )

        `when`(accountRepository.findById(accountId)).thenReturn(Optional.empty())

        val response = accountService.updateAccount(accountId, request)

        assertEquals(null, response)
        verify(accountRepository).findById(accountId)
        verify(accountRepository, never()).save(any(Account::class.java))
    }

    @Test
    fun `should delete account successfully`() {
        val accountId = UUID.randomUUID()

        `when`(accountRepository.existsById(accountId)).thenReturn(true)

        val result = accountService.deleteAccount(accountId)

        assertEquals(true, result)
        verify(accountRepository).existsById(accountId)
        verify(accountRepository).deleteById(accountId)
    }

    @Test
    fun `should return false when deleting non-existent account`() {
        val accountId = UUID.randomUUID()

        `when`(accountRepository.existsById(accountId)).thenReturn(false)

        val result = accountService.deleteAccount(accountId)

        assertEquals(false, result)
        verify(accountRepository).existsById(accountId)
        verify(accountRepository, never()).deleteById(accountId)
    }
}