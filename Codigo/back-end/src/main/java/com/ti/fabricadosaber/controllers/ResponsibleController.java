package com.ti.fabricadosaber.controllers;

import java.net.URI;
import java.util.List;

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

import com.ti.fabricadosaber.models.Responsible;
import com.ti.fabricadosaber.services.ResponsibleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/responsible")
@Validated
public class ResponsibleController {

    @Autowired
    private ResponsibleService responsibleService;

    @GetMapping
    public ResponseEntity<List<Responsible>> listAllResponsibles() {
        List<Responsible> responsibles = responsibleService.listAllResponsibles();
        return ResponseEntity.ok(responsibles);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Responsible> findById(@PathVariable Long id) {
        Responsible objResponsible = this.responsibleService.findById(id);
        return ResponseEntity.ok().body(objResponsible);
    }

    @GetMapping
    public ResponseEntity<List<Responsible>> findAll() {
        List<Responsible> list = responsibleService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Responsible obj) {
        this.responsibleService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Responsible obj, @PathVariable Long id) {
        obj.setId(id);
        this.responsibleService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.responsibleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}