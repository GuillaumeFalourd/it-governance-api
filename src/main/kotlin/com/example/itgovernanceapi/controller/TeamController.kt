package com.example.itgovernanceapi.controller

import com.example.itgovernanceapi.dto.TeamRequestDto
import com.example.itgovernanceapi.dto.TeamResponseDto
import com.example.itgovernanceapi.service.TeamService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/teams")
@Tag(name = "Team Management", description = "APIs for managing teams")
class TeamController(private val teamService: TeamService) {

    @GetMapping
    @Operation(summary = "Get all teams")
    fun getAllTeams(): ResponseEntity<List<TeamResponseDto>> {
        val teams = teamService.getAllTeams()
        return ResponseEntity.ok(teams)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get team by ID")
    fun getTeamById(@PathVariable id: UUID): ResponseEntity<TeamResponseDto> {
        val team = teamService.getTeamById(id)
        return if (team != null) {
            ResponseEntity.ok(team)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    @Operation(summary = "Create a new team")
    fun createTeam(@Valid @RequestBody request: TeamRequestDto): ResponseEntity<TeamResponseDto> {
        val team = teamService.createTeam(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(team)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing team")
    fun updateTeam(@PathVariable id: UUID, @Valid @RequestBody request: TeamRequestDto): ResponseEntity<TeamResponseDto> {
        val team = teamService.updateTeam(id, request)
        return if (team != null) {
            ResponseEntity.ok(team)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a team")
    fun deleteTeam(@PathVariable id: UUID): ResponseEntity<Void> {
        val deleted = teamService.deleteTeam(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}