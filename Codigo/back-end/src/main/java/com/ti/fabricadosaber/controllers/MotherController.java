package com.ti.fabricadosaber.controllers;

import com.ti.fabricadosaber.models.Mother;
import com.ti.fabricadosaber.services.MotherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/mother")
@Validated
public class MotherController {

    @Autowired
    private MotherService motherService;

    @GetMapping("/{id}")
    public ResponseEntity<Mother> findById(@PathVariable Long id) {
        Mother obj = motherService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Mother obj) {
        this.motherService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Mother obj, @PathVariable Long id) {
        obj.setId(id);
        this.motherService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.motherService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
