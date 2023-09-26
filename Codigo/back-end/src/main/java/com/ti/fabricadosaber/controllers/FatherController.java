package com.ti.fabricadosaber.controllers;

import com.ti.fabricadosaber.models.Father;
import com.ti.fabricadosaber.services.FatherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/father")
@Validated
public class FatherController {

    @Autowired
    private FatherService fatherService;

    @GetMapping("/{id}")
    public ResponseEntity<Father> findById(@PathVariable Long id) {
        Father obj = this.fatherService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Father obj) {
        this.fatherService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Father obj, @PathVariable Long id) {
        obj.setId(id);
        this.fatherService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.fatherService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
