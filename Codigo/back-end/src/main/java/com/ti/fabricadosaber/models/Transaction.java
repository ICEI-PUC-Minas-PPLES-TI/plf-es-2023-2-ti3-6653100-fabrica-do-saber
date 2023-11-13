package com.ti.fabricadosaber.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti.fabricadosaber.enums.Category;
import com.ti.fabricadosaber.enums.FinancialFlowType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Transaction.TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    public static final String TABLE_NAME = "Transaction";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "value", nullable = true, updatable = true)
    private Double value;

    @Column(name = "financial_flow_type", length = 45, nullable = false, updatable = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    private FinancialFlowType financialFlowType;

    @Column(name = "description", length = 45, nullable = true, updatable = true)
    private String description;

    @Column(name = "category", length = 45, nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "date", length = 10, nullable = false, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
}
