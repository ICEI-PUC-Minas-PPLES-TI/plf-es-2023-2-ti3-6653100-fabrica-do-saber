package com.ti.fabricadosaber.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
/*@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
*/
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {

    @Column(name = "birth_date", length = 10, nullable = false, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @Column(name = "salary", nullable = false, updatable = true)
    private double salary;

    @Column(name = "hire_date", length = 10, nullable = false, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate hireDate;

    @Column(name = "registration_date", length = 10, nullable = false, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate registrationDate;

    @Column(name = "termination_date", length = 10, nullable = false, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate terminationDate;

}
