package com.ti.fabricadosaber.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti.fabricadosaber.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public abstract class Person {

    @Column(name = "full_name", length = 45, nullable = false, updatable = true)
    @Size(min = 2, max = 100)
    private String fullName;

    @Column(name = "cpf", length = 14, nullable = false, unique = true, updatable = false)
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    private String cpf;

    @Column(name = "rg", length = 45, nullable = false, unique = true, updatable = false)
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}")
    private String rg;

    @Column(name = "email", length = 45, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 45, nullable = false, unique = true, updatable = true)
    @Pattern(regexp = "\\d{2} \\d{5}-\\d{4}")
    private String phoneNumber;

    @Column(name = "address_number", length = 45, nullable = false, updatable = true)
    private String addressNumber;

    @Column(name = "address_complement", length = 45, nullable = true, updatable = true)
    private String addressComplement;

    @Column(name = "street_address", length = 45, nullable = false, updatable = true)
    private String streetAddress;

    @Column(name = "neighborhood", length = 45, nullable = false, updatable = true)
    private String neighborhood;

    @Column(name = "zip_code", length = 45, nullable = false, updatable = true)
    @Pattern(regexp = "\\d{5}-\\d{3}")
    private String zipCode;

    @Column(name = "city_of_Residence", length = 45, nullable = false, updatable = true)
    private String cityOfResidence;

    @Column(name = "home_state", length = 45, nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private State homeState;

    @Column(name = "registration_date", length = 10, nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate registrationDate;

    @Column(name = "birth_date", length = 10, nullable = false, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

}
