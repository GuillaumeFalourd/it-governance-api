package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.AccountRequestDto
import com.example.itgovernanceapi.dto.AccountResponseDto
import com.example.itgovernanceapi.entity.Account
import com.example.itgovernanceapi.repository.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AccountService(private val accountRepository: AccountRepository) {

    fun getAllAccounts(): List<AccountResponseDto> {
        return accountRepository.findAll().map { it.toResponseDto() }
    }

    fun getAccountById(id: UUID): AccountResponseDto? {
        return accountRepository.findById(id).orElse(null)?.toResponseDto()
    }

    @Transactional
    fun createAccount(request: AccountRequestDto): AccountResponseDto {
        val account = Account(
            type = request.type,
            identifier = request.identifier,
            description = request.description
        )
        return accountRepository.save(account).toResponseDto()
    }

    @Transactional
    fun updateAccount(id: UUID, request: AccountRequestDto): AccountResponseDto? {
        val existing = accountRepository.findById(id).orElse(null) ?: return null
        val updated = existing.copy(
            type = request.type,
            identifier = request.identifier,
            description = request.description
        )
        return accountRepository.save(updated).toResponseDto()
    }

    @Transactional
    fun deleteAccount(id: UUID): Boolean {
        return if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    private fun Account.toResponseDto() = AccountResponseDto(
        id = id ?: throw IllegalStateException("Account ID should not be null after saving"),
        type = type,
        identifier = identifier,
        description = description
    )
}