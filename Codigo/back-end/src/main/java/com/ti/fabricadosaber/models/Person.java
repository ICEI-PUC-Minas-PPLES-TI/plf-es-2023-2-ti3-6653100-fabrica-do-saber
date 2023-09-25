package com.ti.fabricadosaber.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name", length = 45, nullable = false, updatable = true)
    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;

    @Column(name = "cpf", length = 14, nullable = false, unique = true, updatable = false)
    @NotBlank
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    private String cpf;

    @Column(name = "rg", length = 45, nullable = false, unique = true, updatable = false)
    @NotBlank
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}")
    private String rg;

    @Column(name = "email", length = 45, nullable = false, unique = true)
    @NotBlank
    private String email;

    @Column(name = "phone_number", length = 45, nullable = false, unique = true, updatable = true)
    @NotBlank
    @Pattern(regexp = "\\d{2} \\d{5}-\\d{4}")
    private String phoneNumber;

    @Column(name = "address_number", length = 45, nullable = false, updatable = true)
    @NotBlank
    private String adressNumber;

    @Column(name = "street_address", length = 45, nullable = false, updatable = true)
    @NotBlank
    private String streetAddress;

    @Column(name = "neighborhood", length = 45, nullable = false, updatable = true)
    @NotBlank
    private String neighborhood;

    @Column(name = "zip_code", length = 45, nullable = false, unique = true, updatable = true)
    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}")
    private String zipCode;

    @Column(name = "city_of_Residence", length = 45, nullable = false, updatable = true)
    @NotBlank
    private String cityOfResidence;

}
