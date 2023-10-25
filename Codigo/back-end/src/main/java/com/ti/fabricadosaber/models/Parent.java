package com.ti.fabricadosaber.models;

import com.ti.fabricadosaber.enums.Relationship;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = Parent.TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parent extends Person {

    public static final String TABLE_NAME = "parent";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "occupation", length = 45, nullable = true, updatable = true)
    private String occupation;

    @Column(name = "company", length = 45, nullable = true)
    private String company;

    @Column(name = "relationship", length = 45, nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

}

