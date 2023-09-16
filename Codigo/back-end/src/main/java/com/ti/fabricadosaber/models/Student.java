package com.ti.fabricadosaber.models;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Student.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	
	public static final String TABLE_NAME = "student";
	
	@Id
	@Column(name = "registration", unique = true )
	private int registration;

	@Column(name = "full_name", length = 45, nullable = false, updatable = true)
	@NotBlank
	@Size(min = 5, max = 45)
	private String fullName;

	@Column(name = "year_registration", length = 10, nullable = false, updatable = true)
	@JsonFormat(pattern="dd-MM-yyyy")
	private LocalDate yearRegistration;
	
	@Column(name = "grade", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String grade;
	
	@Column(name = "education", length = 45, nullable = false, updatable = true)
	@NotBlank
	@Size(min = 5, max = 45)
	private String education;
	
	@Column(name = "date_of_birth", length = 10, nullable = false, updatable = true)
	@NotBlank
	@JsonFormat(pattern="dd-MM-yyyy")
	private LocalDate dateOfBirth;
	
	@Column(name = "city_birth", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String cityBirth;
	
	@Column(name = "state", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String state;
	
	@Column(name = "nationality", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String nationality;
	
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
	
	@Column(name = "parent_name", length = 45, nullable = false, updatable = true)
	@Size(min = 5, max = 45)
	@NotBlank
	private String parentName;
	
	@Column(name = "cpf", length = 14, nullable = false, unique = true, updatable = false)
	@NotBlank
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@Column(name = "rg", length = 45, nullable = false, unique = true, updatable = false)
	@NotBlank
	private String rg;
	
	@Column(name = "occupation", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String occupation;
	
	@Column(name = "company", length = 45, nullable = false, updatable = true)
	private String company;
	
	@Column(name = "phone_number", length = 45, nullable = false, unique = true, updatable = true)
	@NotBlank
	@Pattern(regexp = "\\d{2}) \\d{5}-\\d{4}")
	private String phoneNumber;
	
	@Column(name = "email", length = 45, nullable = false, unique = true, updatable = true)
	@NotBlank
    @Email(message = "E-mail inválido")
	private String email;
	
	@Column(name = "religion", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String religion;
	
	@Column(name = "race", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String race;

}
