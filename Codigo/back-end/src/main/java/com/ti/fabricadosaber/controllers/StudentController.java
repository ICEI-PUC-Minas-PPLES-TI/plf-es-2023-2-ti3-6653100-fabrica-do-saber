package com.ti.fabricadosaber.controllers;

import java.net.URI;
import java.util.List;
import java.util.Set;

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

import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Student.CreateStudent;
import com.ti.fabricadosaber.models.Student.UpdateStudent;
import com.ti.fabricadosaber.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(@PathVariable Long id) {
        Student obj = this.studentService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

/*    @GetMapping
    public ResponseEntity<List<Student>> listAllStudents() {
        List<Student> students = studentService.listAllStudents();
        return ResponseEntity.ok(students);
    }*/

   /* @GetMapping("/{id}/guardians")
    public Set<Guardian> listGuardians(@PathVariable Long id) {
        return studentService.listGuardians(id);
    }*/

    @PostMapping
    @Validated(CreateStudent.class)
    public ResponseEntity<Void> create(@Valid @RequestBody Student obj) {
        this.studentService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

  /*  @PutMapping("/{id}")
    @Validated(UpdateStudent.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Student obj, @PathVariable Long id) {
        obj.setId(id);
        this.studentService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.studentService.delete(id);
        return ResponseEntity.noContent().build();
    }*/
}
