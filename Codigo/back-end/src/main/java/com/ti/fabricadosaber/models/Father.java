package com.ti.fabricadosaber.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = Father.TABLE_NAME)
public class Father extends Parent {

    public static final String TABLE_NAME = "father";

    @OneToMany(mappedBy = "father")
    private List<Student> children;

}
