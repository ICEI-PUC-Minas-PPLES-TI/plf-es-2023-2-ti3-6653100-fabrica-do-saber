package com.ti.fabricadosaber.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ti.fabricadosaber.enums.Grade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("team")
@Table(name = "team")
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

    @Transient
    private Set<Long> studentIds = new HashSet<>();

    @Column(name = "name", nullable = false, updatable = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", length = 45, nullable = false, updatable = true)
    private Grade grade;

    @Column(name = "number_students", nullable = true, updatable = true)
    private Integer numberStudents;

    @Column(name = "classroom", length = 45, nullable = false, updatable = true)
    private String classroom;

    @ManyToOne(optional = true)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

}
