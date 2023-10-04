package com.ti.fabricadosaber.controllers;

import com.ti.fabricadosaber.models.Parent;
import com.ti.fabricadosaber.services.ParentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/parent")
@Validated
public class ParentController {

    @Autowired
    private ParentService parentService;


    @GetMapping("/{id}")
    public ResponseEntity<Parent> findById(@PathVariable Long id) {

        Parent obj = this.parentService.findById(id);

        return ResponseEntity.ok().body(obj);
    }


    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Parent obj) {

        this.parentService.create(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }


    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Parent obj, @PathVariable Long id) {

        obj.setId(id);
        this.parentService.update(obj);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.parentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
