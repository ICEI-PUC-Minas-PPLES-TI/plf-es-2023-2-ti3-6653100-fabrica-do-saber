package com.ti.fabricadosaber.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti.fabricadosaber.enums.Grade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vacatiomTeam")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VacationTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", nullable = true, updatable = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", length = 45, nullable = true, updatable = true)
    private Grade grade;

    @Column(name = "number_students", nullable = true, updatable = true)
    private Integer numberStudents;

    @ManyToMany
    @JoinTable(
            name = "vacationTeam_student",
            joinColumns = @JoinColumn(name = "vacationTeam_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    @Column(name = "classroom", length = 45, nullable = true, updatable = true)
    private String classroom;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "start_date", length = 10, nullable = true, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @Column(name = "end_date", length = 10, nullable = true, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
}
