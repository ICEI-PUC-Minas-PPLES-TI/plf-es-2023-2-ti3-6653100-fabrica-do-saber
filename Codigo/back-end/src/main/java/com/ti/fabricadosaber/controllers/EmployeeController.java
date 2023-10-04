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

import com.ti.fabricadosaber.models.Employee;
import com.ti.fabricadosaber.services.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Employee obj = this.employeeService.findById(id);
        return ResponseEntity.ok().body(obj);
    }


    @GetMapping
    public ResponseEntity<List<Employee>> listAllEmployees() {
        List<Employee> employee = employeeService.listAllEmployees();
        return ResponseEntity.ok(employee);
    }


    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Employee obj) {
        this.employeeService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Employee obj, @PathVariable Long id) {
        obj.setId(id);
        this.employeeService.update(obj);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
