package com.ti.fabricadosaber.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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

    @OneToMany(mappedBy = "teacher")
    private List<Team> teams = new ArrayList<Team>();
}
