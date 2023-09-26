package com.ti.fabricadosaber.models;

import com.ti.fabricadosaber.enums.State;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class Parent extends Person {


    @Column(name = "occupation", length = 45, nullable = true, updatable = true)
    private String occupation;

    @Column(name = "company", length = 45, nullable = true)
    private String company;

    @Enumerated(EnumType.STRING)
    @Column(name = "home_state", length = 2, nullable = true)
    private State homeState;



}

