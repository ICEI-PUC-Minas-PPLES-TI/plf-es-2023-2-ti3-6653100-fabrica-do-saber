package com.ti.fabricadosaber.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.ti.fabricadosaber.models.Teacher;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.services.TeacherService;
import com.ti.fabricadosaber.services.TeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teacher")
@Validated
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PreAuthorize("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> findById(@PathVariable Long id) {
        Teacher obj = this.teacherService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<Teacher>> listAllTeachers() {
        List<Teacher> teachers = this.teacherService.listAllTeachers();
        return ResponseEntity.ok().body(teachers);
    }

    @PreAuthorize("ROLE_ADMIN")
    @GetMapping("/team/{id}")
    public ResponseEntity<List<Team>> listTeams(@PathVariable Long id) {
        List<Team> teams = teacherService.listTeams(id);
        return ResponseEntity.ok().body(teams);
    }



    @PreAuthorize("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Teacher> create(@Valid @RequestBody Teacher obj) {
        this.teacherService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Teacher obj, @PathVariable Long id) {
        obj.setId(id);
        this.teacherService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
