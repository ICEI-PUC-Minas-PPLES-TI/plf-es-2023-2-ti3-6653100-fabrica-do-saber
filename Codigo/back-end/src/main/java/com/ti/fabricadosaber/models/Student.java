package com.ti.fabricadosaber.models;


import com.ti.fabricadosaber.enums.Race;
import java.util.Set;
import com.ti.fabricadosaber.enums.Religion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Student.TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "cpf", column = @Column(nullable = true))
@AttributeOverride(name = "phoneNumber", column = @Column(nullable = true))
@AttributeOverride(name = "rg", column = @Column(nullable = true))
@AttributeOverride(name = "email", column = @Column(nullable = true))
public class Student extends Person {

	public static final String TABLE_NAME = "student";

	public interface CreateStudent {
	}

	public interface UpdateStudent {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;


	@ManyToOne
	@JoinColumn(name = "team_id", nullable = true, updatable = true)
	private Team team;

	@Column(name = "hometown", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String hometown;

	@Column(name = "nationality", length = 45, nullable = false, updatable = true)
	@NotBlank(groups = { CreateStudent.class, UpdateStudent.class })
	private String nationality;

	@Column(name = "race", length = 45, nullable = false, updatable = true)
	@Enumerated(EnumType.STRING)
	private Race race;

	@Column(name = "religion", length = 45, nullable = false, updatable = true)
	@Enumerated(EnumType.STRING)
	private Religion religion;

	@ManyToMany(cascade = CascadeType.PERSIST)
	private Set<Parent> parents = null;

}
