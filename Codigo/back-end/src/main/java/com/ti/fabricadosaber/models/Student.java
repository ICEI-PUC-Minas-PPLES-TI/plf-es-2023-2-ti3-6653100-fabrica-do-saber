package com.ti.fabricadosaber.models;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@Id
	@Column
	private int registration;
	@Column
	private String fullName;
	@Column
	private LocalDate yearRegistration;
	@Column
	private String grade;
	@Column
	private String education;
	@Column
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private LocalDate dateOfBirth;
	@Column
	private String cityBirth;
	@Column
	private String state;
	@Column
	private String nationality;
	@Column
	private String streetAdress;
	@Column
	private int number;
	@Column
	private String neighbourhood;
	@Column
	private String cityResidence;
	@Column
	@Pattern(regexp= "\\d{5}-\\d{3}")
	private String postalCode;
	@Column
	private String parentName;
	@Column
	@CPF
	private String cpf;
	@Column
	private String rg;
	@Column
	private String occupation;
	@Column
	private String company;
	@Column
	@Pattern(regexp= "\\d{2}) \\d{5}-\\d{4}")
	private String phoneNumber;
	@Column
	@Email
	private String email;
	@Column
	private String religion;
	@Column
	private String race;
	
	
	
	
	
	
	
	
	
	
}
