package com.ti.fabricadosaber.models;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

	public interface CreateStudent {
	}

	public interface UpdateStudent {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@ManyToMany
	@JoinTable(
			name = "guardian_student",
			joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "guardian_id", referencedColumnName = "id")
	)
	@NotNull(message="Pelo menos um responsável deve ser fornecido!")
	private Set<Guardian> guardians = null;

	@Column(name = "full_name", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	@Size(groups = { CreateStudent.class, UpdateStudent.class }, min = 5, max = 45)
	private String fullName;

	@Column(name = "registration_date", length = 10, nullable = false, updatable = true)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate registrationDate;

	@Column(name = "grade", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String grade;

//	todo: verificar atributo, se nao for necessario, deletar
//	@Column(name = "education", length = 45, nullable = false, updatable = true)
//	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
//	@Size(groups = { CreateStudent.class, UpdateStudent.class }, min = 5, max = 45)
//	private String education;

	@Column(name = "birth_date", length = 10, nullable = false, updatable = true)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate birthDate;

	@Column(name = "hometown", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String hometown;

	@Column(name = "home_state", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String homeState;

	@Column(name = "nationality", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String nationality;

	@Column(name = "religion", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String religion;

	@Column(name = "race", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String race;

	/*------------------------------------*/
	/*Novos atributos [DELETAR COMENTARIO]*/
	/*------------------------------------*/
	@Column(name = "street_address", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String streetAddress;

	@Column(name = "address_number", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String addressNumber;

	@Column(name = "neighborhood", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String neighborhood;

	@Column(name = "city_of_residence", length = 45, nullable = false, updatable = true)
	@NotBlank
	private String cityOfResidence;

	@Column(name = "zip_code", length = 45, nullable = false, unique = true, updatable = true)
	@NotBlank
	@Pattern(regexp = "\\d{5}-\\d{3}")
	private String zipCode;

}
