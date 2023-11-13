package com.ti.fabricadosaber.controllers;

import java.math.BigDecimal;
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

import com.ti.fabricadosaber.models.Transaction;
import com.ti.fabricadosaber.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
@Validated
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
        Transaction obj = this.transactionService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> listAllFinancialTransactions() {
        List<Transaction> transactions = this.transactionService.listAllFinancialTransactions();
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> TotalTransactions() {
        BigDecimal total = transactionService.totalTransactions();
        return ResponseEntity.ok(total);
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction obj) {
        this.transactionService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Transaction obj, @PathVariable Long id) {
        obj.setId(id);
        this.transactionService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
