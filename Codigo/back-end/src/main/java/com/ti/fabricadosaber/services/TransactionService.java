package com.ti.fabricadosaber.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.enums.FinancialFlowType;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.models.Teacher;
import com.ti.fabricadosaber.models.Transaction;
import com.ti.fabricadosaber.repositories.TransactionRepository;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtil;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction findById(Long id) {
        Transaction transaction = this.transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Transação não encontrada! Id: " + id + ", Tipo: " + Teacher.class.getName()));

        SecurityUtil.checkUser();

        return transaction;
    }

    public List<Transaction> listAllFinancialTransactions() {
        SecurityUtil.checkUser();

        List<Transaction> transactions = this.transactionRepository.findAll();
        if (transactions.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma transação cadastrada");
        }
        return transactions;
    }

    @Transactional
    public Transaction create(Transaction obj) {
        SecurityUtil.checkUser();

        obj.setId(null);
        obj = this.transactionRepository.save(obj);
        return obj;
    }

    public Transaction update(Transaction obj) {
        Transaction newObj = findById(obj.getId());

        BeanUtils.copyProperties(obj, newObj, "id");

        return this.transactionRepository.save(newObj);
    }

    public void delete(Long id) {
        Transaction transaction = findById(id);

        try {
            this.transactionRepository.delete(transaction);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }

    public BigDecimal totalTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        if (transactions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getValue() != null) {
                if (FinancialFlowType.INPUT.equals(transaction.getFinancialFlowType())) {
                    total = total.add(BigDecimal.valueOf(transaction.getValue()));
                } else if (FinancialFlowType.OUTPUT.equals(transaction.getFinancialFlowType())) {
                    total = total.subtract(BigDecimal.valueOf(transaction.getValue()));
                }
            }
        }

        return total;
    }
}
