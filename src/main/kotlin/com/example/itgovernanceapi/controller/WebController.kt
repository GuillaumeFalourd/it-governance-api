package com.example.itgovernanceapi.controller

import com.example.itgovernanceapi.dto.*
import com.example.itgovernanceapi.service.AccountService
import com.example.itgovernanceapi.service.PermissionService
import com.example.itgovernanceapi.service.TeamService
import com.example.itgovernanceapi.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
class WebController(
    private val accountService: AccountService,
    private val permissionService: PermissionService,
    private val userService: UserService,
    private val teamService: TeamService
) {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("accounts", accountService.getAllAccounts())
        model.addAttribute("permissions", permissionService.getAllPermissions())
        model.addAttribute("users", userService.getAllUsers())
        model.addAttribute("teams", teamService.getAllTeams())
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
        model.addAttribute("accounts", accountService.getAllAccounts())
        return "permissions"
    }

    @GetMapping("/web/teams")
    fun teamsPage(model: Model): String {
        model.addAttribute("teams", teamService.getAllTeams())
        model.addAttribute("accounts", accountService.getAllAccounts())
        model.addAttribute("permissions", permissionService.getAllPermissions())
        return "teams"
    }

    @GetMapping("/web/users")
    fun usersPage(model: Model): String {
        model.addAttribute("users", userService.getAllUsers())
        model.addAttribute("teams", teamService.getAllTeams())
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
            val teamIds = (request["teamIds"] as? List<String>)?.map { UUID.fromString(it) }?.toSet() ?: setOf()

            val userRequest = UserRequestDto(
                name = request["name"] as String,
                companyEmail = request["companyEmail"] as String,
                githubAccount = request["githubAccount"] as String,
                teamIds = teamIds
            )
            val user = userService.createUser(userRequest)
            mapOf("success" to true, "data" to user)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @PostMapping("/api/teams")
    @ResponseBody
    fun createTeam(@RequestBody request: Map<String, Any>): Map<String, Any> {
        return try {
            val accountIds = (request["accountIds"] as? List<String>)?.map { UUID.fromString(it) }?.toSet() ?: setOf()
            val permissionIds = (request["permissionIds"] as? List<String>)?.map { UUID.fromString(it) }?.toSet() ?: setOf()

            val teamRequest = TeamRequestDto(
                name = request["name"] as String,
                description = request["description"] as? String,
                accountIds = accountIds,
                permissionIds = permissionIds
            )
            val team = teamService.createTeam(teamRequest)
            mapOf("success" to true, "data" to team)
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @PutMapping("/api/teams/{id}")
    @ResponseBody
    fun updateTeam(@PathVariable id: UUID, @RequestBody request: Map<String, Any>): Map<String, Any> {
        return try {
            val teamRequest = TeamRequestDto(
                name = request["name"] as String,
                description = request["description"] as? String
            )
            val team = teamService.updateTeam(id, teamRequest)
            if (team != null) {
                mapOf("success" to true, "data" to team)
            } else {
                mapOf("success" to false, "error" to "Team not found")
            }
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @PostMapping("/api/teams/{teamId}/accounts/{accountId}")
    @ResponseBody
    fun addAccountToTeam(@PathVariable teamId: UUID, @PathVariable accountId: UUID): Map<String, Any> {
        return try {
            val team = teamService.addAccountToTeam(teamId, accountId)
            if (team != null) {
                mapOf("success" to true, "data" to team)
            } else {
                mapOf("success" to false, "error" to "Team or account not found")
            }
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @DeleteMapping("/api/teams/{teamId}/accounts/{accountId}")
    @ResponseBody
    fun removeAccountFromTeam(@PathVariable teamId: UUID, @PathVariable accountId: UUID): Map<String, Any> {
        return try {
            val team = teamService.removeAccountFromTeam(teamId, accountId)
            if (team != null) {
                mapOf("success" to true, "data" to team)
            } else {
                mapOf("success" to false, "error" to "Team not found")
            }
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @PostMapping("/api/teams/{teamId}/permissions/{permissionId}")
    @ResponseBody
    fun addPermissionToTeam(@PathVariable teamId: UUID, @PathVariable permissionId: UUID): Map<String, Any> {
        return try {
            val team = teamService.addPermissionToTeam(teamId, permissionId)
            if (team != null) {
                mapOf("success" to true, "data" to team)
            } else {
                mapOf("success" to false, "error" to "Team or permission not found")
            }
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }

    @DeleteMapping("/api/teams/{teamId}/permissions/{permissionId}")
    @ResponseBody
    fun removePermissionFromTeam(@PathVariable teamId: UUID, @PathVariable permissionId: UUID): Map<String, Any> {
        return try {
            val team = teamService.removePermissionFromTeam(teamId, permissionId)
            if (team != null) {
                mapOf("success" to true, "data" to team)
            } else {
                mapOf("success" to false, "error" to "Team not found")
            }
        } catch (e: Exception) {
            mapOf("success" to false, "error" to e.message.orEmpty())
        }
    }
}