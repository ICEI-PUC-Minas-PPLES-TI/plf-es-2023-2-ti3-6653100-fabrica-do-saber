package com.ti.fabricadosaber.controllers;

import java.net.URI;
import java.util.List;

import com.ti.fabricadosaber.dto.TeamResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.stream.Collectors;

import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.services.TeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/team")
@Validated
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> findById(@PathVariable Long id) {
        Team team = teamService.findById(id);

        if (team == null)
            return ResponseEntity.notFound().build();

        TeamResponseDTO teamResponseDTO = this.teamService.convertToTeamResponseDTO(team);

        return ResponseEntity.ok(teamResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> listAllTeams() {
        List<Team> teams = this.teamService.listAllTeams();

        List<TeamResponseDTO> teamResponseDTOs = teams.stream()
                .map(this.teamService::convertToTeamResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(teamResponseDTOs);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> listStudents(@PathVariable Long id) {
        List<Student> students = this.teamService.listStudents(id);
        return ResponseEntity.ok().body(students);
    }

    @PostMapping
    public ResponseEntity<Team> create(@Valid @RequestBody Team obj) {
        this.teamService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{Id}/add-students")
    public ResponseEntity<Void> addStudentsToTeam(
            @PathVariable Long Id,
            @RequestBody List<Long> studentIds
    ) {
        this.teamService.addStudentToTeam(Id, studentIds);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Team obj, @PathVariable Long id) {
        obj.setId(id);
        this.teamService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teamId}/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long teamId, @PathVariable Long studentId) {
        this.teamService.deleteStudent(teamId, studentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.teamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
