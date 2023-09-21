package com.ti.fabricadosaber.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
			name = "student_responsible",
			joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "responsible_id", referencedColumnName = "id")
	)
	@NotNull(message="Pelo menos um responsável deve ser fornecido!")
	private Set<Responsible> responsibles = null;

//	@ManyToOne
//	@JoinColumn(name = "responsible1_id", nullable = true)
//	private Responsible responsible1;
//
//	@ManyToOne
//	@JoinColumn(name = "responsible2_id", nullable = true)
//	private Responsible responsible2;

	@Column(name = "full_name", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	@Size(groups = { CreateStudent.class, UpdateStudent.class }, min = 5, max = 45)
	private String fullName;

	@Column(name = "year_registration", length = 10, nullable = false, updatable = true)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate yearRegistration;

	@Column(name = "grade", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String grade;

	@Column(name = "education", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	@Size(groups = { CreateStudent.class, UpdateStudent.class }, min = 5, max = 45)
	private String education;

	@Column(name = "date_of_birth", length = 10, nullable = false, updatable = true)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dateOfBirth;


	@Column(name = "city_birth", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String cityBirth;

	@Column(name = "state", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String state;

	@Column(name = "nationality", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String nationality;

	@Column(name = "religion", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String religion;

	@Column(name = "race", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String race;

}
