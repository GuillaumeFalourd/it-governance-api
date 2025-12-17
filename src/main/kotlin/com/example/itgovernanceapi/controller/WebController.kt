package com.example.itgovernanceapi.controller

import com.example.itgovernanceapi.dto.*
import com.example.itgovernanceapi.service.AccountService
import com.example.itgovernanceapi.service.PermissionService
import com.example.itgovernanceapi.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
class WebController(
    private val accountService: AccountService,
    private val permissionService: PermissionService,
    private val userService: UserService
) {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("accounts", accountService.getAllAccounts())
        model.addAttribute("permissions", permissionService.getAllPermissions())
        model.addAttribute("users", userService.getAllUsers())
        return "index"
    }

    @GetMapping("/web/accounts")
    fun accountsPage(model: Model): String {
        model.addAttribute("accounts", accountService.getAllAccounts())
        model.addAttribute("accountTypes", listOf("AWS", "GITHUB", "STACKSPOT"))
        return "accounts"
    }

    @GetMapping("/web/permissions")
    fun permissionsPage(model: Model): String {
        model.addAttribute("permissions", permissionService.getAllPermissions())
        model.addAttribute("accountTypes", listOf("AWS", "GITHUB", "STACKSPOT"))
        return "permissions"
    }

    @GetMapping("/web/users")
    fun usersPage(model: Model): String {
        model.addAttribute("users", userService.getAllUsers())
        return "users"
    }

    // API endpoints for AJAX calls from the UI
    @PostMapping("/api/accounts")
    @ResponseBody
    fun createAccount(@ModelAttribute request: AccountRequestDto): Map<String, Any> {
        return try {
            val account = accountService.createAccount(request)
            mapOf("success" to true, "data" to account)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @DeleteMapping("/api/accounts/{id}")
    @ResponseBody
    fun deleteAccount(@PathVariable id: UUID): Map<String, Any> {
        return try {
            accountService.deleteAccount(id)
            mapOf("success" to true)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @PostMapping("/api/permissions")
    @ResponseBody
    fun createPermission(@ModelAttribute request: PermissionRequestDto): Map<String, Any> {
        return try {
            val permission = permissionService.createPermission(request)
            mapOf("success" to true, "data" to permission)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @DeleteMapping("/api/permissions/{id}")
    @ResponseBody
    fun deletePermission(@PathVariable id: UUID): Map<String, Any> {
        return try {
            permissionService.deletePermission(id)
            mapOf("success" to true)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @PostMapping("/api/users")
    @ResponseBody
    fun createUser(@RequestBody request: Map<String, Any>): Map<String, Any> {
        return try {
            val userRequest = UserRequestDto(
                name = request["name"] as String,
                companyEmail = request["companyEmail"] as String,
                githubAccount = request["githubAccount"] as String,
                githubOrganizations = (request["githubOrganizations"] as? List<String>) ?: listOf(),
                awsAccounts = (request["awsAccounts"] as? List<String>) ?: listOf()
            )
            val user = userService.createUser(userRequest)
            mapOf("success" to true, "data" to user)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @DeleteMapping("/api/users/{id}")
    @ResponseBody
    fun deleteUser(@PathVariable id: UUID): Map<String, Any> {
        return try {
            userService.deleteUser(id)
            mapOf("success" to true)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }
}