package com.ti.fabricadosaber.models;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = Responsible.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Responsible {

    public static final String TABLE_NAME = "responsible";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String nome;

    @Column(name = "cpf", length = 14, nullable = false, unique = true, updatable = false)
    @NotBlank
    @CPF(message = "CPF inválido")
    private String cpf;

    @Column(name = "rg", length = 45, nullable = false, unique = true, updatable = false)
    @NotBlank
    private String rg;

    @Column(name = "email", length = 45, nullable = false, unique = true)
    @NotBlank
    @Email(message = "E-mail inválido")
    private String email;

    @Column(name = "occupation", length = 45, nullable = false, updatable = true)
    @NotBlank
    private String occupation;

    @Column(name = "phone_number", length = 45, nullable = false, unique = true, updatable = true)
    @NotBlank
    @Pattern(regexp = "\\d{2}) \\d{5}-\\d{4}")
    private String phoneNumber;

    @Column(name = "company", length = 45, nullable = false, updatable = true)
    private String company;

    @Column(name = "street_adress", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String streetAdress;
	
	@Column(name = "number", length = 45, nullable = false, updatable = true)
	@NotBlank
	private int number;
	
	@Column(name = "neighbourhood", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String neighbourhood;
	
	@Column(name = "city_residence", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String cityResidence;
	
	@Column(name = "postal_code", length = 45, nullable = false, unique = true, updatable = true)
	@NotBlank
	@Pattern(regexp = "\\d{5}-\\d{3}")
	private String postalCode;

}
