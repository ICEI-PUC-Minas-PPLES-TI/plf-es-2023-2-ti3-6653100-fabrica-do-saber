package com.ti.fabricadosaber.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ti.fabricadosaber.enums.Grade;

import jakarta.persistence.*;
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

    @Column(name = "name", nullable = false, updatable = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", length = 45, nullable = false, updatable = true)
    private Grade grade;

    @Column(name = "number_students", nullable = true, updatable = true)
    private Integer numberStudents;


/*    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Student> students = new ArrayList<>();*/

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Student> students;

    @Column(name = "classroom", length = 45, nullable = false, updatable = true, unique = true)
    @NotNull
    @NotEmpty
    private String classroom;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher; 
}
