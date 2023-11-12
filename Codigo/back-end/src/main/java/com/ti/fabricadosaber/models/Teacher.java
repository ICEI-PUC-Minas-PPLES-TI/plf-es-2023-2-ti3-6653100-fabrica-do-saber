package com.ti.fabricadosaber.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ti.fabricadosaber.enums.AcademicFormationStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Teacher.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Teacher extends Employee {

    public static final String TABLE_NAME = "teacher";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @OneToMany(mappedBy = "teacher")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Team> teams = new ArrayList<Team>();

    @Column(name = "academic_formation", length = 45, nullable = false, updatable = true)
    @NotBlank
    private String academicFormation;

    @Column(name = "academic_formation_status", length = 45, nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private AcademicFormationStatus academicFormationStatus;
}
