package com.ti.fabricadosaber.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = Mother.TABLE_NAME)
public class Mother extends Parent {

    public static final String TABLE_NAME = "mother";
    @OneToMany(mappedBy = "mother")
    private List<Student> children;


}
