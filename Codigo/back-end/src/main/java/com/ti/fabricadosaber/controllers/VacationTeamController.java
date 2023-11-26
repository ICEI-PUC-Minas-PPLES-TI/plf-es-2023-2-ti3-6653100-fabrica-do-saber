package com.ti.fabricadosaber.controllers;

import java.net.URI;
import java.util.List;

import com.ti.fabricadosaber.dto.StudentResponseDTO;
import com.ti.fabricadosaber.dto.VacationTeamResponseDTO;
import com.ti.fabricadosaber.services.VacationTeamService;
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
import com.ti.fabricadosaber.models.VacationTeam;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vacation-team")
@Validated
public class VacationTeamController {
    
    @Autowired
    private VacationTeamService vacationTeamService;


    @GetMapping("/{id}")
    public ResponseEntity<VacationTeamResponseDTO> findById(@PathVariable Long id) {
        VacationTeamResponseDTO obj = this.vacationTeamService.findByIdDTO(id);
        return ResponseEntity.ok().body(obj);
    }


     @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentResponseDTO>> listStudents(@PathVariable Long id) {
        List<StudentResponseDTO> studentList = this.vacationTeamService.listStudents(id);
        return ResponseEntity.ok().body(studentList);
    }


    @PostMapping
    public ResponseEntity<VacationTeam> create(@Valid @RequestBody VacationTeam obj) {
        this.vacationTeamService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody VacationTeam obj, @PathVariable Long id) {
        obj.setId(id);
        this.vacationTeamService.update(obj);
        return ResponseEntity.noContent().build();
    }


/*    @DeleteMapping("/{vacationTeamId}/delete-students")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long vacationTeamId, @RequestBody List<Long> studentIds) {
        
        //this.vacationTeamService.deleteStudentFromVacationTeam(vacationTeamId, studentIds);
        return ResponseEntity.noContent().build();
    }*/


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.vacationTeamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
