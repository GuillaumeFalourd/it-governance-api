package com.example.itgovernanceapi.service

import com.example.itgovernanceapi.dto.PermissionRequestDto
import com.example.itgovernanceapi.dto.PermissionResponseDto
import com.example.itgovernanceapi.entity.AccountType
import com.example.itgovernanceapi.entity.Permission
import com.example.itgovernanceapi.repository.PermissionRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
class PermissionServiceTest {

    @Mock
    private lateinit var permissionRepository: PermissionRepository

    private lateinit var permissionService: PermissionService

    private val testId = UUID.randomUUID()
    private val testPermission = Permission(
        id = testId,
        accountType = AccountType.AWS,
        name = "READ",
        description = "Read permission"
    )

    @BeforeEach
    fun setUp() {
        permissionService = PermissionService(permissionRepository)
    }

    @Test
    fun `getAllPermissions should return all permissions as DTOs`() {
        // Given
        val permissions = listOf(testPermission)
        `when`(permissionRepository.findAll()).thenReturn(permissions)

        // When
        val result = permissionService.getAllPermissions()

        // Then
        assertEquals(1, result.size)
        assertEquals(testId, result[0].id)
        assertEquals(AccountType.AWS, result[0].accountType)
        assertEquals("READ", result[0].name)
        assertEquals("Read permission", result[0].description)
        verify(permissionRepository).findAll()
    }

    @Test
    fun `getPermissionById should return permission when found`() {
        // Given
        `when`(permissionRepository.findById(testId)).thenReturn(Optional.of(testPermission))

        // When
        val result = permissionService.getPermissionById(testId)

        // Then
        assertNotNull(result)
        assertEquals(testId, result?.id)
        assertEquals(AccountType.AWS, result?.accountType)
        assertEquals("READ", result?.name)
        assertEquals("Read permission", result?.description)
        verify(permissionRepository).findById(testId)
    }

    @Test
    fun `getPermissionById should return null when not found`() {
        // Given
        `when`(permissionRepository.findById(testId)).thenReturn(Optional.empty())

        // When
        val result = permissionService.getPermissionById(testId)

        // Then
        assertNull(result)
        verify(permissionRepository).findById(testId)
    }

    @Test
    fun `createPermission should create and return new permission`() {
        // Given
        val request = PermissionRequestDto(
            accountType = AccountType.GITHUB,
            name = "WRITE",
            description = "Write permission"
        )
        val savedPermission = Permission(
            id = testId,
            accountType = request.accountType,
            name = request.name,
            description = request.description
        )
        `when`(permissionRepository.save(any(Permission::class.java))).thenReturn(savedPermission)

        // When
        val result = permissionService.createPermission(request)

        // Then
        assertNotNull(result)
        assertEquals(testId, result.id)
        assertEquals(AccountType.GITHUB, result.accountType)
        assertEquals("WRITE", result.name)
        assertEquals("Write permission", result.description)
        verify(permissionRepository).save(any(Permission::class.java))
    }

    @Test
    fun `updatePermission should update and return permission when found`() {
        // Given
        val request = PermissionRequestDto(
            accountType = AccountType.STACKSPOT,
            name = "ADMIN",
            description = "Admin permission"
        )
        val updatedPermission = testPermission.copy(
            accountType = request.accountType,
            name = request.name,
            description = request.description
        )
        `when`(permissionRepository.findById(testId)).thenReturn(Optional.of(testPermission))
        `when`(permissionRepository.save(updatedPermission)).thenReturn(updatedPermission)

        // When
        val result = permissionService.updatePermission(testId, request)

        // Then
        assertNotNull(result)
        assertEquals(testId, result?.id)
        assertEquals(AccountType.STACKSPOT, result?.accountType)
        assertEquals("ADMIN", result?.name)
        assertEquals("Admin permission", result?.description)
        verify(permissionRepository).findById(testId)
        verify(permissionRepository).save(updatedPermission)
    }

    @Test
    fun `updatePermission should return null when permission not found`() {
        // Given
        val request = PermissionRequestDto(
            accountType = AccountType.AWS,
            name = "ADMIN",
            description = "Admin permission"
        )
        `when`(permissionRepository.findById(testId)).thenReturn(Optional.empty())

        // When
        val result = permissionService.updatePermission(testId, request)

        // Then
        assertNull(result)
        verify(permissionRepository).findById(testId)
        verify(permissionRepository, never()).save(any(Permission::class.java))
    }

    @Test
    fun `deletePermission should return true when permission exists`() {
        // Given
        `when`(permissionRepository.existsById(testId)).thenReturn(true)

        // When
        val result = permissionService.deletePermission(testId)

        // Then
        assertTrue(result)
        verify(permissionRepository).existsById(testId)
        verify(permissionRepository).deleteById(testId)
    }

    @Test
    fun `deletePermission should return false when permission does not exist`() {
        // Given
        `when`(permissionRepository.existsById(testId)).thenReturn(false)

        // When
        val result = permissionService.deletePermission(testId)

        // Then
        assertFalse(result)
        verify(permissionRepository).existsById(testId)
        verify(permissionRepository, never()).deleteById(testId)
    }

    @Test
    fun `getPermissionsByAccountType should return permissions for specific account type`() {
        // Given
        val permissions = listOf(testPermission)
        `when`(permissionRepository.findByAccountType(AccountType.AWS)).thenReturn(permissions)

        // When
        val result = permissionService.getPermissionsByAccountType(AccountType.AWS)

        // Then
        assertEquals(1, result.size)
        assertEquals(testId, result[0].id)
        assertEquals(AccountType.AWS, result[0].accountType)
        assertEquals("READ", result[0].name)
        assertEquals("Read permission", result[0].description)
        verify(permissionRepository).findByAccountType(AccountType.AWS)
    }
}