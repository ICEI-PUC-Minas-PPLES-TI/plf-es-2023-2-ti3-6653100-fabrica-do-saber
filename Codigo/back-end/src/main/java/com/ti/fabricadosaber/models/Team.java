package com.ti.fabricadosaber.models;

import java.util.ArrayList;
import java.util.List;

import com.ti.fabricadosaber.enums.Grade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Team")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Team {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", length = 45, nullable = false, updatable = true)
    @NotBlank
    private Grade grade;

    @Column(name = "number_students", nullable = false, updatable = true)
    @NotNull
    @NotEmpty
    private Integer numberStudents;

    @OneToMany(mappedBy = "team")
    @Column(name = "students", nullable = false, updatable = true)
    @NotNull
    @NotEmpty
    private List<Student> students = new ArrayList<Student>();

    @Column(name = "room", length = 45, nullable = false, updatable = true)
    @NotNull
    @NotEmpty
    private String room;

    @ManyToOne
    @Column(name = "teacher", nullable = false, updatable = true)
    @NotNull
    @NotEmpty
    private Teacher teacher;

    
    
}
